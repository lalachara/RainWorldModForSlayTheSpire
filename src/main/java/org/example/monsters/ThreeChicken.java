package org.example.monsters;


import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;
import org.example.powers.CatchBuff;
import org.example.powers.SleepBuff;
import org.example.powers.ToothBuff;

public class ThreeChicken extends CustomMonster {
    // 怪物ID（此处的ModHelper在“04 - 本地化”中提到）
    private static final String ID = ("Rainworld:ThreeChicken");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    // 怪物的图片，请自行添加
    public boolean wuhaihua = false;
    public int sleeptimes = 0;
    public ThreeChicken(float x, float y) {
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
        super(NAME, ID, 140, 0.0F, 50.0F, 250.0F, 400.0F, null, x, y-50F);
        //loadAnimation("images/CharacterImg/Monster/Cabrite/skeleton.atlas", "images/CharacterImg/Monster/Cabrite/skeleton.json", 1.0F);

        this.atlas = new TextureAtlas("images/CharacterImg/Monster/ThreeChicken/threechicken.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        json.setScale(Settings.renderScale*0.8F );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/ThreeChicken/threechicken.json"));

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
            this.setHp(260, 270);
        } else {
            this.setHp(270, 280);
        }

        // 意图0的伤害
        this.damage.add(new DamageInfo(this, AbstractDungeon.ascensionLevel >= 3 ? 8 : 6));

    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        this.setMove((byte)0, Intent.BUFF);
        AbstractDungeon.effectList.add(
                new ThoughtBubble(
                        hb.cX-100F,
                        hb.cY+100F,
                        3.0F, // 持续时间
                        monsterStrings.DIALOG[2], // 你想让角色说的话
                        false // 是否朝右
                )
        );
        this.createIntent();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }
    private boolean checkdiscard(){
        if(AbstractDungeon.player.discardPile.isEmpty()){
            return false;
        }
        int temp = 0;
        for (AbstractCard c:AbstractDungeon.player.discardPile.group){
            if(c.type== AbstractCard.CardType.ATTACK){
                temp++;
            }else if(c.type== AbstractCard.CardType.SKILL){
                temp--;
            }
        }
        return temp>0;
    }
    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        boolean atk =checkdiscard();
        // 有40%的概率执行意图0，60%执行意图1
        if(!lastMove((byte) 5)){
            if(atk){
                if(!this.hasPower(CatchBuff.POWER_ID)) {
                    this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                }else {
                    this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base, this.getPower(CatchBuff.POWER_ID).amount, true);
                }
            }else {
                this.setMove((byte) 2, Intent.STRONG_DEBUFF);
            }
        }else {
            if(sleeptimes<1){
                this.setMove((byte) 5, Intent.SLEEP);
                sleeptimes++;
            }else {
                this.setMove((byte) 4, Intent.ESCAPE);
            }

        }
    }
    public void damage(DamageInfo info) {
        int previousHealth = this.currentHealth;
        super.damage(info);
        if (this.currentHealth != previousHealth && this.wuhaihua) {
            this.setMove((byte)0, Intent.BUFF);
            createIntent();
            this.wuhaihua = false;
            this.state.setAnimation(0, "idle", true);
            this.sleeptimes = 0;
            AbstractDungeon.effectList.add(
                    new ThoughtBubble(
                            hb.cX-100F,
                            hb.cY+100F,
                            3.0F, // 持续时间
                            monsterStrings.DIALOG[2], // 你想让角色说的话
                            false // 是否朝右
                    )
            );
        }

    }

    @Override
    public void die() {
        if(AbstractDungeon.player instanceof SlugCat&&AbstractDungeon.player.maxHealth>=20){
            AchievementMgr.unlockAchievement(34);
        }
        super.die();
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        int temp;
        switch (this.nextMove) {
            case 0://观测，如果技能多于攻击，下回合执行攻击，否则执行特殊行动。
                //每吃过一个神经元加1特殊值。
                temp = 0;
                if(AbstractDungeon.player instanceof SlugCat){
                     temp = ((SlugCat)AbstractDungeon.player).eattimes;
                }
                addToTop(new ApplyPowerAction(this, this, new CatchBuff(this, temp), temp));
                break;
            case 1://攻击，每一层buff额外攻击一次。
                temp = this.getPower(CatchBuff.POWER_ID)!=null?this.getPower(CatchBuff.POWER_ID).amount:0;
                for (int i = 0; i < temp; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 2://减少生命上限，凝滞1张牌。
                AbstractDungeon.player.decreaseMaxHealth(1+AbstractDungeon.player.maxHealth/10);
                AbstractDungeon.actionManager.addToBottom(new ApplyStasisAction(null));
                break;
            case 3://被无害化后变蓝，空过
                break;
            case 4://逃跑
                AbstractDungeon.effectList.add(
                        new ThoughtBubble(
                                hb.cX-100F,
                                hb.cY+100F,
                                3.0F, // 持续时间
                                monsterStrings.DIALOG[1], // 你想让角色说的话
                                false // 是否朝右
                        )
                );
                addToBot(new WaitAction(3F));
                addToBot(new EscapeAction(this));
                AchievementMgr.unlockAchievement(24);
                break;
            case 5://bb
                if(sleeptimes==1)
                        AbstractDungeon.effectList.add(
                                new ThoughtBubble(
                                        hb.cX-100F,
                                        hb.cY+100F,
                                        3.0F, // 持续时间
                                        monsterStrings.DIALOG[0], // 你想让角色说的话
                                        false // 是否朝右
                                )
                        );
                        break;


        }


        // 要加一个rollmove的action，重roll怪物的意图
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}