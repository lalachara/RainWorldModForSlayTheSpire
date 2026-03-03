package org.example.Actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;

import java.util.Iterator;
import java.util.Objects;

public class CorruptMotivateAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    // This class is a placeholder for the CorruptMotivateAction.
    // You can implement the specific logic for this action here.

    public CorruptMotivateAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        // Example logic: Corrupt the first card in the hand\
        Iterator card = AbstractDungeon.player.hand.group.iterator();
        while (card.hasNext()) {
            AbstractCard c = (AbstractCard) card.next();
            if (Objects.equals(c.cardID, RedCat_OriginalCorrupt.ID)){
                //c.use(AbstractDungeon.player,null);
            }
        }
        this.isDone = true;
    }
}
