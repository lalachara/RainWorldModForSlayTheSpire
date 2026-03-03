package org.example.cards.RedCat.attacks;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.RedCat.RedMushroomBuff;

public class RedCat_EatBlood extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:EatBlood";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/EatBlood.png";
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
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_EatBlood() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    this.misc = 0;
    this.damage = this.baseDamage = this.misc+6;
    this.magicNumber = this.baseMagicNumber = this.misc+4;
    this.exhaust =true;
    this.haveUpgradedDescription = true;
    initializeDescription();
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

        addToBot(new AddTemporaryHPAction(p,p,this.magicNumber));

    }
    public void addMisc() {
        this.addToBot(new IncreaseMiscAction(this.uuid, this.misc, upgraded?3:2));
    }
    public void applyPowers() {
        this.damage = this.baseDamage = this.misc+6;
        this.magicNumber = this.baseMagicNumber = this.misc+4;
        super.applyPowers();
        this.initializeDescription();
    }
    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        this.misc+=2;
        this.damage = this.baseDamage = this.misc+8;
        this.magicNumber = this.baseMagicNumber = this.misc+4;
        this.upgradedDamage = true;
        this.upgradedMagicNumber = true;
        upgradeName();
        initializeDescription();
    }
}
}