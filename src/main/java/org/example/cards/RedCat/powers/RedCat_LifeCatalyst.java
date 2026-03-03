package org.example.cards.RedCat.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.LifeCatalyst;
import org.example.powers.TogetherBuff;

public class RedCat_LifeCatalyst extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:LifeCatalyst";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/null.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.POWER;
    // 卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_LifeCatalyst() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new LifeCatalyst(p,1),1));
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}