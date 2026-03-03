package org.example.cards.RedCat.skills;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.RedCat;
import org.example.cards.RedCat.RedCatCard;

import static org.example.Character.SlugCat.Enums.CantCorrupt_Tag;
import static org.example.Character.SlugCat.Enums.Corrupt_Tag;

public class RedCat_Calm extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Calm";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Clam.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Calm() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE,  RARITY, TARGET);
        this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(CantCorrupt_Tag);
        initializeDescription();
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount= this.block;
        if(p instanceof RedCat){
            amount+= ((RedCat) p).var*magicNumber;
        }
      addToBot(new GainBlockAction(p, p, amount));
      if(p instanceof RedCat){
          RedCat redCat = (RedCat) p;
          redCat.addCorrupt(-5);
      }
    }


    public void applyPowersToBlock() {
    int amount = 0;
        if(AbstractDungeon.player instanceof RedCat){
            RedCat rc = (RedCat) AbstractDungeon.player;
            amount = rc.var*magicNumber;
        }

        this.baseBlock+=amount;
        super.applyPowersToBlock();
        this.baseBlock-=amount;

   }


    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeBlock(2);
        upgradeMagicNumber(1);
        upgradeName();
        initializeDescription();
    }
}
}