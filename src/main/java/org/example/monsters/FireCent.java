package org.example.monsters;


import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import org.example.Achievement.AchievementMgr;
import org.example.powers.FireCentBuff;
import org.example.powers.ToothBuff;

public class FireCent extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = ("Rainworld:FireCent");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加

    public FireCent(float x, float y) {
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
        super(NAME, ID, 140, 0.0F, 150F, 250.0F, 320.0F, null, x, y-150F);
        //loadAnimation("images/CharacterImg/Monster/Cabrite/skeleton.atlas", "images/CharacterImg/Monster/Cabrite/skeleton.json", 1.0F);

        this.atlas = new TextureAtlas("images/CharacterImg/Monster/FireCent/lieyanwugong-SP.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas);
        json.setScale(Settings.renderScale*0.8F );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/FireCent/lieyanwugong-SP.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(0.7F);
//        loadAnimation("images/CharacterImg/Monster/Cabrite/skeleton.atlas", "images/CharacterImg/Monster/Cabrite/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(114, 124);
        } else {
            this.setHp(119, 129);
        }

        // 怪物伤害意图的数值

        // 意图0的伤害
        this.damage.add(new DamageInfo(this, AbstractDungeon.ascensionLevel >= 3?16:14));
        this.damage.add(new DamageInfo(this, AbstractDungeon.ascensionLevel >= 3?22:18));
        // 意图1的伤害
    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        addToBot(new ApplyPowerAction(this,this,new FireCentBuff(this,AbstractDungeon.ascensionLevel >= 8?50:40),AbstractDungeon.ascensionLevel >= 8?50:40));
        addToBot(new GainBlockAction(this, this, AbstractDungeon.ascensionLevel >= 18?50:40));
        this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base,2,true);
        this.createIntent();
    }

    @Override
    public void update() {
        super.update();

        float deltaTime = Gdx.graphics.getDeltaTime();
        state.update(deltaTime);
        state.apply(skeleton);
        skeleton.updateWorldTransform();

        // 调试：打印路径约束的关键参数




    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        // 有40%的概率执行意图0，60%执行意图1
       if(lastMove((byte)0)){
           this.setMove((byte) 1, Intent.ATTACK_DEBUFF, damage.get(0).base);
       }else if(lastMove((byte)1)){
              this.setMove((byte) 2, Intent.BUFF);
       }else if(lastMove((byte)2)){
           this.setMove((byte) 3, Intent.ATTACK_DEBUFF, damage.get(1).base);
       }else {
           this.setMove((byte) 0, Intent.ATTACK, damage.get(0).base,2,true);
       }
    }

    @Override
    public void die() {
        AchievementMgr.unlockAchievement(19);
        super.die();
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (this.nextMove) {
            case 0://2连击
                //this.state.setAnimation(1, "atk", false);
                //this.state.addAnimation(0, "idle", true,0.0F);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1://单体 脆弱+易伤
                //this.state.setAnimation(1, "atk2", false);
                //this.state.addAnimation(0, "idle", true,0.0F);
                //this.state.setAnimation(0, "idle", true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new VulnerablePower(AbstractDungeon.player,AbstractDungeon.ascensionLevel >= 18?3:2,true),AbstractDungeon.ascensionLevel >= 18?3:2));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new FrailPower(AbstractDungeon.player,AbstractDungeon.ascensionLevel >= 18?3:2,true),AbstractDungeon.ascensionLevel >= 18?3:2));
                break;
            case 2://强化
                addToBot(new ApplyPowerAction(this,this,new StrengthPower(this,AbstractDungeon.ascensionLevel >= 18?4:2),AbstractDungeon.ascensionLevel >= 18?4:2));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                addToBot(new MakeTempCardInDrawPileAction(new Dazed(), AbstractDungeon.ascensionLevel >= 18?3:2, true, true));
                break;

        }

        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}