package org.example.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import org.example.Achievement.AchievementMgr;
import org.example.powers.ProliferationBuff;

public class MushRoom2 extends MushRoom {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ("Liver:MushRoom");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    int baseHP = 0;
    // 怪物的图片，请自行添加
    public static final String IMG = "images/CharacterImg/Monster/patrick.png";

    public MushRoom2(float x, float y) {
        // 参数的作用分别是：
        // 名字
        // ID
        // 最大生命值，由于在之后还会设置可以随意填写
        // hitbox偏移量 - x方向
        // hitbox偏移量 - y方向
        // hitbox大小 - x方向（会影响血条宽度）
        // hitbox大小 - y方向
        // 图片
        // 怪物位置（x,y）
        super( x, y);
        this.atlas = new TextureAtlas("images/CharacterImg/Monster/MushRoom/skeleton.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale*0.95F );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/MushRoom/skeleton.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "sleep2", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(550,550);
        } else {
            this.setHp(600,600);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel < 9) {
            slashDmg = 12;
            multiDmg = 35;
        } else {
            slashDmg = 15;
            multiDmg = 46;
        }
        // 意图0的伤害
        this.damage.add(new DamageInfo(this, slashDmg));
        // 意图1的伤害
        this.damage.add(new DamageInfo(this, multiDmg));
    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        addToBot(new ApplyPowerAction(this,this,new ProliferationBuff(this,AbstractDungeon.ascensionLevel >= 9?10: 8)));
        this.setMove((byte)3, Intent.SLEEP);
        this.createIntent();
    }
    public void wake(){
        this.state.setAnimation(0, "sleeptoidle2", false);
        this.state.addAnimation(0, "idle", true,0.0F);
        isSleep = false;
        setMove((byte) 5, Intent.DEBUFF);
        createIntent(); // 更新意图
        baseHP = this.currentHealth;

    }
    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        if (isSleep) {
            if(Sleep>0) {
                this.setMove((byte) 3, Intent.SLEEP);
                Sleep--;
                return;
            }else{
                this.state.setAnimation(0, "sleeptoidle2", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                isSleep = false;
            }

        }

        if(currentHealth>baseHP*3&&baseHP!=0)
        {
            this.setMove((byte) 4, Intent.ATTACK,damage.get(1).base,9,true);
            return;
        }
//        if(currentHealth<200&&!Awake){
//            Awake = true;
//            this.setMove((byte) 2,Intent.BUFF);
//            return;
//        }
        // 有40%的概率执行意图0，60%执行意图1
        if(num < 30){
            this.setMove((byte) 5, Intent.DEBUFF);
        }else if(num < 65){
            this.setMove((byte) 1, Intent.ATTACK, damage.get(0).base,3,true);
        }else
            this.setMove((byte) 0,Intent.ATTACK, damage.get(1).base);


    }

    @Override
    public void die() {
        AchievementMgr.unlockAchievement(18);
        if(AbstractDungeon.player instanceof Defect)
            AchievementMgr.unlockAchievement(22);
        super.die();
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0://单体
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1://3连击
                for (int i = 0; i < 2; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2://首次低于200时随机消耗一张牌，将生命值和最大生命值平分
                int temp = (currentHealth+maxHealth)/2;
                this.maxHealth = temp;
                addToBot(new HealAction(this,this,temp-currentHealth));
                AbstractCard c  = AbstractDungeon.player.drawPile.getRandomCard(true);
                if(c!=null)
                    addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.drawPile));
                healthBarUpdatedEvent();
                break;
            case 3://开局睡眠
                break;
            case 4://生命大于1600开始斩杀
                for (int i = 0; i < 9; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }
                break;
            case 5://塞眩晕
                addToBot(new MakeTempCardInDiscardAction(
                        new Dazed(), (AbstractDungeon.ascensionLevel >= 9)?5:3
                ));
                break;
        }


        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}