package org.example.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.RedCat;
import org.example.cards.Liver.skills.Liver_Fruit;
import org.example.cards.Liver.skills.Liver_SmallCent;
import org.example.cards.RedCat.skills.RedCat_SmallCent;

import java.util.ArrayList;


public class ReadyBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:ReadyBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private ArrayList<AbstractCard> cards = new ArrayList<>();


    public ReadyBuff(AbstractCreature owner, int amount,AbstractCard card) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.description = DESCRIPTIONS[0];

        this.cards.add(card);
        loadRegion("carddraw");
        this.updateDescription();
    }


    public void specifyCard(AbstractCard card) {
        this.cards.add(card);
        updateDescription();
    }
    @Override
    public void atStartOfTurn() {


        for (AbstractCard card : cards) {
                addToBot(new MakeTempCardInHandAction(card));
        }
        cards.clear();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
    }

//    @Override
//    public void onAfterCardPlayed(AbstractCard card) {
//        if(card.type == AbstractCard.CardType.ATTACK) { // 检查是否是攻击牌
//            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
//        }
//    }
    @Override
    public void updateDescription() {
        String cardNames = "";
        for (AbstractCard card : cards) {
            cardNames += "[ #y" + card.name + " ]";
        }
        this.description = DESCRIPTIONS[0]+cardNames+DESCRIPTIONS[1]; ;
    }
}
