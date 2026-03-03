package org.example.cards.RedCat.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Achievement.AchievementMgr;
import org.example.cards.RedCat.RedCatCard;

public class RedCat_FireWind extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:FireWind";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/FireWind.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_FireWind() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 2;
        this.misc = 1;
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(null);
        for (int i = 0; i < magicNumber; i++) {
            addToBot(
                    new DamageAllEnemiesAction(
                            p,
                            this.multiDamage,
                            this.damageTypeForTurn,
                            AbstractGameAction.AttackEffect.FIRE
                    )
            );
            addToBot(new DrawCardAction(p,this.misc) );
        }

    }


    @Override
    public boolean canUpgrade() {
        /* 58 */     return true;
        /*    */   }
    @Override
    public void upgrade() {
        upgradeDamage(1 );
        upgradeMagicNumber(timesUpgraded%2);
        this.timesUpgraded++;
        this.upgraded = true;
        if(Settings.language==Settings.GameLanguage.ZHS)
            this.name = cardStrings.NAME + "第" + this.timesUpgraded+"层";
        else
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
        if(timesUpgraded>=13){
            AchievementMgr.unlockAchievement(20);
        }
    }
}