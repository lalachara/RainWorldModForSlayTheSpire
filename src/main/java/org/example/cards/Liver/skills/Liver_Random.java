package org.example.cards.Liver.skills;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Capacitor;
import com.megacrit.cardcrawl.cards.blue.Defragment;
import com.megacrit.cardcrawl.cards.blue.Loop;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.purple.ForeignInfluence;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.cards.Liver.attacks.Liver_FoodHit;
import org.example.cards.Liver.attacks.Liver_StrangeBomb;
import org.example.cards.Liver.attacks.Liver_Strike;
import org.example.cards.Liver.attacks.Liver_ThreeAgree;
import org.example.cards.Liver.powers.*;
import org.example.cards.Liver.attacks.*;

import org.example.powers.ChuangBuff;
import org.example.powers.OverSleepDeBuff;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static org.example.Character.SlugCat.Enums.Liver_Color;


public class Liver_Random extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:Random";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_Random.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_Random() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int random = AbstractDungeon.cardRandomRng.random(upgraded?21:11);
        if(p instanceof SlugCat) {
            SlugCat liver = (SlugCat) p;
            if(random%2==1)
                CardCrawlGame.sound.play("Liver:random1");
            else
                CardCrawlGame.sound.play("Liver:random2");
            switch (random) {
                case 0://业力+1
                    liver.addWorkLevel(1);
                    break;
                case 1://抽4张牌
                    if(upgraded)
                        addToBot(new DrawCardAction(5));
                    else
                        addToBot(new DrawCardAction(3));
                    break;
                case 2://随机药水
                    addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                    if(upgraded)
                        addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
                    break;
                case 3://3能量
                    if(upgraded)
                        addToBot(new GainEnergyAction(9));
                    else
                        addToBot(new GainEnergyAction(3));
                    break;
                case 4://格挡
                    addToBot(new GainBlockAction(p, p, 10));
                    break;
                case 5://5饱食度
                    liver.addFood(5);
                    break;
                case 6://对自己10伤害
                    addToBot(new DamageAction(p, new DamageInfo(p, 10, DamageInfo.DamageType.NORMAL)));
                    break;
                case 7://休眠
                    liver.sleep(liver.hasPower(OverSleepDeBuff.POWER_ID));
                    AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
                    break;
                case 8://3张粘液
                    addToBot(new com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction(
                            new Slimed(), 3));
                    break;
                case 9://2层创伤
                    addToBot(new ApplyPowerAction(p, p, new ChuangBuff(p, 3)));
                    break;
                case 10://无事发生
                    break;
                case 12:
                    addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
                    addToBot(new SkipEnemiesTurnAction());
                    addToBot(new PressEndTurnButtonAction());
                    break;
                case 13:
                    for (int i = 0; i < 40; i++) {
                        AbstractDungeon.effectList.add(
                                new GainPennyEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)
                        );
                    }
                    addToBot(new GainGoldAction(40));
                    break;
                case 14:
                    addToBot(new ApotheosisAction());
                    break;
                case 15://打出排队顶的3张牌
                    addToBot(new PlayTopCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true));
                    addToBot(new PlayTopCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true));
                    addToBot(new PlayTopCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), true));
                    break;
                case 17://渎神
                    this.addToBot(new ChangeStanceAction("Divinity"));
                    this.addToBot(new ApplyPowerAction(p, p, new EndTurnDeathPower(p)));
                    break;
                case 18://减少力量
                    addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -3)));
                    break;
                case 20:
                    use(p,m);
                    use(p,m);
                    use(p,m);
                    break;
                case 21:
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (mo.hasPower("BackAttack")) {
                        use(p,m);
                        return;
                    }
                    if (mo.type == AbstractMonster.EnemyType.BOSS) {
                        use(p,m);
                        return;
                        }
                    }
                    AbstractPlayer abstractPlayer = AbstractDungeon.player;
                    if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                          (AbstractDungeon.getCurrRoom()).smoked = true;
                          addToBot(new VFXAction(new SmokeBombEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY)));
                         AbstractDungeon.player.hideHealthBar();
                           AbstractDungeon.player.isEscaping = true;
                          AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
                         AbstractDungeon.overlayMenu.endTurnButton.disable();
                           AbstractDungeon.player.escapeTimer = 2.5F;
                    }
                default:
                    GetRandomTempCard();
                    addToTop(new DiscardAction(player, player, player.hand.size(), true));
                    break;
            }
        }




    }
    public void GetRandomTempCard(){
        int i = AbstractDungeon.cardRandomRng.random(14);
        switch (i){
            case 0://逆转命运+业报+三重肯定
                addToTop(new MakeTempCardInHandAction(new Liver_ThreeAgree()));
                addToTop(new MakeTempCardInHandAction(new Liver_TwistFate()));
                addToTop(new MakeTempCardInHandAction(new Liver_WorkError()));
                break;
            case 1://3打3防
                for (int j = 0; j < 3; j++) {
                    addToTop(new MakeTempCardInHandAction(new Liver_Strike()));
                }
                for (int j = 0; j < 3; j++) {
                    addToTop(new MakeTempCardInHandAction(new Liver_Defend()));
                }
                break;
            case 2://5他山之石
                for (int j = 0; j < 5; j++) {
                    addToTop(new MakeTempCardInHandAction(new ForeignInfluence()));
                }
                break;
            case 3://5个蓝果
                for (int j = 0; j < 5; j++) {
                    addToTop(new MakeTempCardInHandAction(new Liver_Fruit()));
                }

                break;
            case 4://5张仙人指路
                AbstractCard card = new Liver_Random();
                card.upgrade();
                for (int j = 0; j < 5; j++) {
                    addToTop(new MakeTempCardInHandAction(card));
                }
                break;
            case 5://众生平等和3张奇点炸弹
                addToTop(new MakeTempCardInHandAction(new Liver_Balance()));
                addToTop(new MakeTempCardInDrawPileAction(new Liver_StrangeBomb(),3,false,true,false));
                break;
            case 6://大补汤
                addToTop(new MakeTempCardInHandAction(new Dazed()));
                addToTop(new MakeTempCardInHandAction(new Slimed()));
                addToTop(new MakeTempCardInHandAction(new Wound()));
                addToTop(new MakeTempCardInHandAction(new Burn()));
                addToTop(new MakeTempCardInHandAction(new VoidCard()));
                break;
            case 7://无事发生
                break;
            case 8://悔恨和仙人指路
                addToTop(new MakeTempCardInHandAction(new Regret()));
                addToTop(new MakeTempCardInHandAction(new Liver_Random()));
                break;
            case 9://3张废牌
                addToTop(new MakeTempCardInHandAction(new Capacitor()));
                addToTop(new MakeTempCardInHandAction(new Defragment()));
                addToTop(new MakeTempCardInHandAction(new Loop()));
                break;
            case 10://蓝果+改变现实
                AchievementMgr.unlockAchievement(10);
                addToTop(new MakeTempCardInHandAction(new Liver_Fruit()));
                addToTop(new ApplyPowerAction( player, player,new MasterRealityPower(player), 1));
                break;
            case 11://2矛大师笔记+猫之宝库
                addToTop(new MakeTempCardInHandAction(new Liver_SpearMasterNote()));
                addToTop(new MakeTempCardInHandAction(new Liver_CatThesaurus()));
                addToTop(new MakeTempCardInHandAction(new Liver_SpearMasterNote()));
                break;
            case 12://更强壮的蛞蝓猫+4猫猫拳
                addToTop(new MakeTempCardInHandAction(new Liver_Strike()));
                addToTop(new MakeTempCardInHandAction(new Liver_Strike()));
                addToTop(new MakeTempCardInHandAction(new Liver_StrongerCat()));
                addToTop(new MakeTempCardInHandAction(new Liver_Strike()));
                addToTop(new MakeTempCardInHandAction(new Liver_Strike()));
                break;
            case 13://5张猫崽
                for (int j = 0; j < 5; j++) {
                    addToTop(new MakeTempCardInHandAction(new Liver_Kitty()));
                }
                break;
            case 14://
                addToTop(new MakeTempCardInHandAction(new Liver_SmallCent()));
                addToTop(new MakeTempCardInHandAction(new Liver_Fruit()));
                addToTop(new MakeTempCardInHandAction(new Liver_FatWorld()));
                addToTop(new MakeTempCardInHandAction(new Liver_Ruminate()));
                addToTop(new MakeTempCardInHandAction(new Liver_FoodHit()));
                break;
            default:break;


        }
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; // 更新描述
            initializeDescription(); // 刷新卡牌描述
        }
    }
}