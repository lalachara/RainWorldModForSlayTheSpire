package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.cards.Liver.skills.Liver_Pearl;

import static org.example.tools.Tools.getCardByID;


public class CheckPearlAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractCard card = getCardByID(AbstractDungeon.player.hand,Liver_Pearl.ID);
        while(card!=null){
            AbstractDungeon.player.hand.moveToExhaustPile(card);
            card.use(AbstractDungeon.player,null);
            card = getCardByID(AbstractDungeon.player.hand,Liver_Pearl.ID);
        }
        isDone = true;

    }
}
