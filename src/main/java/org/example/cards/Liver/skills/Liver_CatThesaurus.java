package org.example.cards.Liver.skills;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import static org.example.Character.SlugCat.Enums.Liver_Color;
import static org.example.tools.Tools.GetRandomSpearAndAddTag;

public class Liver_CatThesaurus extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:CatThesaurus";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_CatBag.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST =1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = AbstractCard.CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_CatThesaurus() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true; // 设置消耗标签
        glowColor = Color.GOLD.cpy();;

}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

//        addToBot(new DiscardAction(p,p,p.hand.size(),false));
//        addToBot(new ApplyPowerAction(p,p,new AllSpearFreeBuff(p,1),1));
//        addToBot(new GetAllSpearAction());
        AbstractCard card = GetRandomSpearAndAddTag(upgraded);
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));

}


    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        this.upgradeBaseCost(0);
        initializeDescription();
        upgradeName();
    }
}
}