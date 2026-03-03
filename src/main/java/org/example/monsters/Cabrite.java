package org.example.monsters;


import basemod.BaseMod;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.Achievement.AchievementMgr;
import org.example.powers.ToothBuff;

public class Cabrite extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ("Liver:Cabrite");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    public static final String IMG = "images/CharacterImg/Monster/patrick.png";

    public Cabrite(float x, float y) {
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
        //loadAnimation("images/CharacterImg/Monster/Cabrite/skeleton.atlas", "images/CharacterImg/Monster/Cabrite/skeleton.json", 1.0F);

        this.atlas = new TextureAtlas("images/CharacterImg/Monster/Cabrite/skeleton.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/Cabrite/skeleton.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.0F);
//        loadAnimation("images/CharacterImg/Monster/Cabrite/skeleton.atlas", "images/CharacterImg/Monster/Cabrite/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(38, 45);
        } else {
            this.setHp(35, 42);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 8) {
            slashDmg = 11;
            multiDmg = 3;
        } else {
            slashDmg = 13;
            multiDmg = 5;
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
        addToBot(new GainBlockAction(this,12));
        this.setMove((byte)3, Intent.BUFF);
        this.createIntent();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        int move;
        if(currentHealth<10)
            this.setMove((byte) 2, Intent.ESCAPE);
        else if (num < 30) {
            this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
        } else if(num<50){
            this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base, 3, true);
        }else if(num<80){
            this.setMove((byte) 4, Intent.DEFEND);
        }
        else
            this.setMove((byte) 5,Intent.BUFF);
    }

    @Override
    public void die() {
        AchievementMgr.unlockAchievement(16);
        super.die();
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0:
                this.state.setAnimation(1, "atk", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1:
                this.state.setAnimation(1, "atk2", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                //this.state.setAnimation(0, "idle", true);
                for (int i = 0; i < 2; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                break;
            case 3:
                addToBot(new ApplyPowerAction(this,this,new ToothBuff(this,1),1));
                break;
            case 4:
                addToBot(new GainBlockAction(this,12));
                break;
            case 5:
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,3),3));
        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}