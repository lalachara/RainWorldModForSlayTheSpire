package org.example.cards.Liver.skills;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.ExhaustAndGainEnergyAction;
import org.example.Character.*;

import static org.example.Character.SlugCat.Enums.Liver_Color;

public class Liver_ReUseLJ extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:ReUseLJ";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_ReUseLJ.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 0;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_ReUseLJ() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.exhaust = true;
    this.magicNumber = this.baseMagicNumber = 1;
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof SlugCat) {
            SlugCat liver = (SlugCat) p;
            liver.food -= 2;
            addToBot(new ExhaustAndGainEnergyAction(magicNumber, false, true, true));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        // 检查玩家是否是 wishdell 类
        if (p instanceof SlugCat) {
            SlugCat liver = (SlugCat) p;
            if(liver.food>=2)
                return true;
            else
            {
                this.cantUseMessage = "饱食度不足，无法使用。";
                return false;
            }
        }
        return false;
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeMagicNumber(1);
        upgradeName();
    }
}
}