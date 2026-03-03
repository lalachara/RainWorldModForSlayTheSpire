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
import org.example.Achievement.AchievementMgr;
import org.example.powers.IronFlyBuff;


public class IronBird extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ("Liver:IronBird");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    public int flytimes = AbstractDungeon.ascensionLevel >= 8?6:5;
    private boolean isflying = true;
    public IronBird(float x, float y) {
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
        super(NAME, ID, 140, 0.0F, 0.0F, 250.0F, 400.0F, null, x, y);

        this.atlas = new TextureAtlas("images/CharacterImg/Monster/IronBird/skeleton.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/IronBird/skeleton.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.0F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idel", true);
        e.setTime(e.getEndTime() * MathUtils.random());


        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(166, 176);
        } else {
            this.setHp(170, 180);
        }

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 8) {
            slashDmg = 28;
            multiDmg = 2;
        } else {
            slashDmg = 36;
            multiDmg = 3;
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
        addToBot(new ApplyPowerAction(this,this,new IronFlyBuff(this,flytimes)));
        this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base, 5, true);
        this.createIntent();


    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
        if(lastMove((byte) 4) ){
            this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base, 5, true);
            return;
        }
        if(lastMove((byte) 1)||lastMove((byte) 0)){
            this.setMove((byte) 2, Intent.BUFF);
            return;
        }
        if(lastMove((byte) 3)||lastMove((byte) 2)){
            this.setMove((byte) 4, Intent.DEBUFF);
            return;
        }
        if(!this.hasPower(FlightPower.POWER_ID) ){
            this.setMove((byte) 3, Intent.BUFF);
            return;
        }


       if(num<40){
            this.setMove((byte) 1, Intent.ATTACK, damage.get(1).base, 5, true);
        }else if(num<80){
            this.setMove((byte) 2, Intent.BUFF);
        }
        else
            this.setMove((byte) 4,Intent.DEBUFF);
    }
    public void changeIntent(Byte move){
        switch (move){
            case 0:
                this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base, 2, true);
                this.createIntent(); // 更新意图
                break;
            case 5:
                setMove((byte)5, Intent.STUN);
                this.createIntent(); // 更新意图
                this.state.addAnimation(0, "fall", true,0.0F);
                addToBot(new GainBlockAction(this,AbstractDungeon.ascensionLevel >= 8?30:20));
                if(hasPower(StrengthPower.POWER_ID)){
                    addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));
                }
                break;
        }

    }
    @Override
    public void die() {
        AchievementMgr.unlockAchievement(17);
        super.die();
    }
    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0://2连击
                this.state.setAnimation(1, "atk", false);
                this.state.addAnimation(0, "idel", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1://5连击
                this.state.setAnimation(1, "atk", false);
                this.state.addAnimation(0, "idel", true,0.0F);
                for (int i = 0; i < 4; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2://力量
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,3)));
                break;
            case 3://飞行
                flytimes++;
                AnimationState.TrackEntry e = this.state.setAnimation(0, "idel", true);
                addToBot(new ApplyPowerAction(this,this,new IronFlyBuff(this,flytimes)));
                break;
            case 4://脆弱和易伤
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new FrailPower(AbstractDungeon.player,2,true)));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new VulnerablePower(AbstractDungeon.player,2,true)));
                break;
            case 5://地面获得护甲
                this.state.setAnimation(1, "fall-to-idel", false);
                this.state.addAnimation(0, "fallidel", true,0.0F);
                addToBot(new GainBlockAction(this,AbstractDungeon.ascensionLevel >= 8?30:20));
                break;

        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}