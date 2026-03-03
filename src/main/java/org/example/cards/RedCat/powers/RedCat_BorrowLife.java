package org.example.cards.RedCat.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.BorrowLifeBuff;

public class RedCat_BorrowLife extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:BorrowLife";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/DontTouch.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.POWER;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_BorrowLife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        //exhaust = true; // 设置卡牌为消耗
        //this.isInnate = true; // 设置卡牌为固有

        this.magicNumber = this.baseMagicNumber = 1; // 设置工作等级增加量
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof SlugCat) {
            //((SlugCat) p).addWorkLevel(magicNumber);
            addToBot(new ApplyPowerAction(p,p,new BorrowLifeBuff(p, magicNumber), magicNumber));
        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.initializeDescription();

        }

    }



}