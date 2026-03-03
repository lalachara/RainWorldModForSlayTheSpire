package org.example.cards.Liver.attacks;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Achievement.AchievementMgr;
import org.example.Actions.WhenKillEnemyAction;
import org.example.powers.ChuangBuff;

import static org.example.Character.SlugCat.Enums.Liver_Color;
import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class Liver_SpearShape extends CustomCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:SpearShape";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_SpearFire.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 3;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = AbstractCard.CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_SpearShape() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage  = this.baseDamage = 12;
        this.tags.add(Spear_TAG);
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        WhenKillEnemyAction action = new WhenKillEnemyAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), 3);
        action.c = this;
        addToBot(action);

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        super.calculateCardDamage(mo);
        if(mo.hasPower(ChuangBuff.POWER_ID)) {
            damage+= mo.getPower(ChuangBuff.POWER_ID).amount;

        }
    }

    @Override
    public boolean canUpgrade() {
        /* 58 */     return true;
        /*    */   }
    @Override
    public void upgrade() {
        upgradeDamage(2 + this.timesUpgraded);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
        if(this.timesUpgraded>=10){
            AchievementMgr.unlockAchievement(11);
        }
    }
}