package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AddCardQueueItemAction extends AbstractGameAction {
    AbstractCard tmp;
    AbstractMonster m;

    public AddCardQueueItemAction(AbstractCard tmp, AbstractMonster m) {
        this.tmp = tmp;
        this.m = m;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m,0, true, true), true);
        this.isDone = true;
    }
}
