package org.example.monsters;


import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.example.Achievement.AchievementMgr;
import org.example.powers.LoveBloodBuff;

public class RedCabrite extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    private static final String ID = ("Liver:RedCabrite");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    boolean isHeavyAtk = false;

    public boolean isAtk = false;



    public RedCabrite(float x, float y) {
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
        super(NAME, ID, 140, 0.0F, 0.0F, 250.0F, 270.0F, null, x, y);
        this.atlas = new TextureAtlas("images/CharacterImg/Monster/RedCabrite/skeleton.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/RedCabrite/skeleton.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.2F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(175, 185);
        } else {
            this.setHp(180, 190);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 8) {
            slashDmg = 33;
            multiDmg = 8;
        } else {
            slashDmg = 36;
            multiDmg = 10;
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
        if(AbstractDungeon.ascensionLevel >= 8)
            addToBot(new GainBlockAction(this,40));
        else
            addToBot(new GainBlockAction(this,50));

        addToBot(new ApplyPowerAction(this,this,new BarricadePower(this)));
        this.setMove((byte)3, Intent.BUFF);
        this.createIntent();
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        if(currentHealth<30){
            this.setMove((byte) 2, Intent.ESCAPE);
            return;
        }


        if(lastMove((byte) 3)|| lastMove((byte) 4)|| lastMove((byte) 5)) {
            if(isHeavyAtk){
                this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
                return;
            }

            else{
                this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base,3,true);
                return;
            }

        }
        if(lastMove((byte) 0)) {
            this.setMove((byte) 4, Intent.DEFEND_BUFF);
            return;
        }
        if (lastMove((byte) 1)) {
            this.setMove((byte) 5, Intent.DEBUFF);
            return;
        }

    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0://单体
                this.state.setAnimation(0, "atk", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2,true), 2));
                isHeavyAtk = false;
                break;
            case 1://三连击
                this.state.setAnimation(0, "atk", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                for (int i = 0; i < 2; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                isHeavyAtk = true;
                break;
            case 2://逃跑
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                break;
            case 3://上buff
                CardCrawlGame.sound.play("Liver:XiyiBuff");
                addToBot(new ApplyPowerAction(this,this,new LoveBloodBuff(this,1),1));
                break;
            case 4://获得格挡力量
                addToBot(new GainBlockAction(this,20));
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,2)));
                switch (MathUtils.random(0, 1)) {
                    case 0:
                        CardCrawlGame.sound.play("Liver:xiyi1");
                        break;
                    case 1:
                        CardCrawlGame.sound.play("Liver:xiyi2");
                        break;
                }
                break;
            case 5://3粘液、虚弱
                this.state.setAnimation(0, "tukoushui", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                addToBot(new com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction(
                        new Slimed(), (AbstractDungeon.ascensionLevel >= 8)?4:3
                ));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,2,true)));
                break;
        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void die() {
        AchievementMgr.unlockAchievement(16);
        super.die();
        CardCrawlGame.sound.play("Liver:xiyidie");
    }
}