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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;

public class GreenCabrite extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    private static final String ID = ("Liver:GreenCabrite");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private int lastHp = 0;
    public GreenCabrite(float x, float y) {
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
        super(NAME, ID, 140, 30.0F, 0.0F, 250.0F, 270.0F, null, x, y);
        this.atlas = new TextureAtlas("images/CharacterImg/Monster/GreenCabrite/skeleton.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale *0.65F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/GreenCabrite/skeleton.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.setHp(100, 110);
        } else if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(95, 100);
        }else
            this.setHp(90, 95);

        // 怪物伤害意图的数值
        int normalDmg;
        int heavyDmg;
        if (AbstractDungeon.ascensionLevel >= 17) {
            normalDmg = 8;
            heavyDmg = 18;
        } else if( AbstractDungeon.ascensionLevel >= 7) {
            normalDmg = 6;
            heavyDmg = 16;
        }else
        {
            normalDmg = 5;
            heavyDmg = 15;
        }
        // 意图0的伤害
        this.damage.add(new DamageInfo(this, normalDmg));
        // 意图1的伤害
        this.damage.add(new DamageInfo(this, heavyDmg));
    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        if(AbstractDungeon.ascensionLevel >= 17)
            addToBot(new GainBlockAction(this,20));
        else if (AbstractDungeon.ascensionLevel >= 7)
            addToBot(new GainBlockAction(this,15));
        else
            addToBot(new GainBlockAction(this,10));

        addToBot(new ApplyPowerAction(this,this,new SlowPower(this,1),1));
        this.setMove((byte)0, Intent.ATTACK,damage.get(0).base);
        this.createIntent();
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        if(currentHealth<15){
            this.setMove((byte) 2, Intent.ESCAPE);
            return;
        }
        if(lastMove((byte) 4)) {
                this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base);
                return;
        }
        if(lastMove((byte) 0)) {
            this.setMove((byte) 4, Intent.UNKNOWN);
            return;
        }
        if (lastMove((byte) 1)) {
            this.setMove((byte) 3, Intent.BUFF);
            return;
        }
        if (lastMove((byte) 3)) {
            this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base);
            return;
        }

    }

    @Override
    public void update() {
        super.update();
        if(lastHp==0)
            lastHp=currentHealth;
        else if(lastHp<currentHealth){
            lastHp = currentHealth;
        }else if(lastHp>currentHealth){
            lastHp = currentHealth;
            this.state.setAnimation(0, "behit", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
        }
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0://普通
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1://蓄力击
                this.state.setAnimation(0, "attack", false);
                this.state.addAnimation(0, "idle", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2://逃跑
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
                break;
            case 3://上buff
                CardCrawlGame.sound.play("Liver:XiyiBuff");
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,AbstractDungeon.ascensionLevel >= 17?5:4),AbstractDungeon.ascensionLevel >= 17?5:4));
                break;
            case 4://蓄力
                CardCrawlGame.sound.play("Liver:GreenWenhao");
                break;
        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void die() {
        CardCrawlGame.sound.play("Liver:GreenDie");
        super.die();
    }
}