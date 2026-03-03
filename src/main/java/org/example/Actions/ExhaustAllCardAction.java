package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;


public class ExhaustAllCardAction extends AbstractGameAction {


    @Override
    public void update() {

        for (int i = 0; i < AbstractDungeon.player.drawPile.size(); i++) {
            addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.group.get(i),AbstractDungeon.player.drawPile,true));
        }
        for (int i = 0; i < AbstractDungeon.player.discardPile.size(); i++) {
            addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.discardPile.group.get(i),AbstractDungeon.player.discardPile,true));
        }
        for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
            addToTop(new ExhaustSpecificCardAction(AbstractDungeon.player.hand.group.get(i),AbstractDungeon.player.discardPile,true));
        }
        isDone = true;

    }
}