package org.example.cards.Liver.skills;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.CheckPearlAction;
import org.example.cards.Patch.MultiPreviewCard;
import org.example.cards.Liver.attacks.Liver_Bomb;
import org.example.cards.Liver.attacks.Liver_SpearBoom;
import org.example.powers.ChiefBuff;

import static org.example.Character.SlugCat.Enums.Liver_Color;
import static org.example.Character.SlugCat.Enums.Treasure_TAG;

public class Liver_TreasureBag extends MultiPreviewCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:TreasureBag";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_Tbag.png";
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
    private static final CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_TreasureBag() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.exhaust =true;
    this.tags.add(Treasure_TAG);

        previewCardIDs.add(Liver_SpearBoom.ID);
        previewCardIDs.add(Liver_Bomb.ID);
        previewCardIDs.add(Liver_Pearl.ID);


}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1 = new Liver_SpearBoom();
        AbstractCard c2 = new Liver_Bomb();
        AbstractCard c3 = new Liver_Pearl();

        if (this.upgraded) {
            c1.upgrade();
            c2.upgrade();
            c3.upgrade();
        }
        if(p.hasPower(ChiefBuff.POWER_ID)){
            c1.setCostForTurn(0);
            c1.cost = 0;
            c2.setCostForTurn(0);
            c2.cost = 0;

        }
        addToBot(new CheckPearlAction());
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c1)
        );
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c2)
        );
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c3)
        );

//        if(p.hasPower(FreeSpearBuff.POWER_ID)){
//            addToBot(new FreeSpearAction());
//        }


}


    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeName();
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; // 设置升级后的描述
        initializeDescription(); // 更新描述
    }
}
}