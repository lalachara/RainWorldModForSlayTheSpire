package org.example.cards.RedCat;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.powers.SleepBuff;
import org.example.relics.Liver_SleepPotions;
import org.example.tools.Tools;

import static org.example.Character.SlugCat.Enums.*;

public class RedCatCard extends CustomCard
{
    private static final CardColor COLOR = RedCat_Color;
    private static final CardStrings KeyWordString = CardCrawlGame.languagePack.getCardStrings("RainWorld:KeyWorld");
    public String DESCRIPTIONID;
    protected boolean haveUpgradedDescription = false;
    AbstractCard CorruptedCard;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCatCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION,
                      CardType TYPE, CardRarity RARITY, CardTarget TARGET) {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    if(DESCRIPTIONID==null)
        DESCRIPTIONID = ID;
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    //AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
}
    public AbstractCard GetCorrupted(){

        return CorruptedCard;
    }

//    public void Corrupt(){
//        AbstractCard CorruptedCard ;
//        if(this.hasTag(Spear_TAG))
//            CorruptedCard = new RedCat_SpearCorrupt();
//        else{
//            CorruptedCard = new RedCat_Corrupt();
//            Tools.CorruptCard(CorruptedCard);
//        }
//
//        //Tools.CorruptCard(CorruptedCard);
//        if(CorruptedCard.canUpgrade()&&this.upgraded)
//            CorruptedCard.upgrade();
//        addToBot(new MakeTempCardInHandAction(CorruptedCard,1));
//
//    }


    @Override
    public void initializeDescription() {
        if(hasTag(Corrupt_Tag))
            this.glowColor = Color.PURPLE;
        if(DESCRIPTIONID!=null)
            this.rawDescription = haveUpgradedDescription&&upgraded?CardCrawlGame.languagePack.getCardStrings(DESCRIPTIONID).UPGRADE_DESCRIPTION:CardCrawlGame.languagePack.getCardStrings(DESCRIPTIONID).DESCRIPTION;
        if(hasTag(Immunity_Tag)){
            this.rawDescription = KeyWordString.EXTENDED_DESCRIPTION[4]+" NL "+this.rawDescription;
        }
        if(retain||isInnate||selfRetain)
        {
            this.rawDescription = " NL "+this.rawDescription;
            if(isInnate)
                this.rawDescription = KeyWordString.EXTENDED_DESCRIPTION[1]+this.rawDescription;
            if(retain||selfRetain)
                this.rawDescription = KeyWordString.EXTENDED_DESCRIPTION[0]+this.rawDescription;
        }
        if(isEthereal||exhaust)
        {
            this.rawDescription = this.rawDescription+" NL ";
            if(isEthereal)
                this.rawDescription = this.rawDescription+KeyWordString.EXTENDED_DESCRIPTION[2];
            if(exhaust)
                this.rawDescription = this.rawDescription+KeyWordString.EXTENDED_DESCRIPTION[3];
        }
            super.initializeDescription();
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeName();
    }
    if(CorruptedCard!=null&&CorruptedCard.canUpgrade())
        CorruptedCard.upgrade();
}
}