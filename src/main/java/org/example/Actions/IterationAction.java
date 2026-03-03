package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Objects;

public class IterationAction extends AbstractGameAction {

    public IterationAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;
        this.card = card;
    }
    AbstractCard card;
    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if(Objects.equals(c.cardID, card.cardID)){
                addToBot(new PlayCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), c));

                break;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(Objects.equals(c.cardID, card.cardID)){
                addToBot(new PlayCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), c));

                break;
            }
        }
        this.isDone = true;
    }
}
