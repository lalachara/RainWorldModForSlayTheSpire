package org.example.Actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelectCardsInHandRetainAction extends AbstractGameAction {
    private final AbstractPlayer player;


    public SelectCardsInHandRetainAction() {
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open("选择一张手牌", 1, false, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                card.retain = true;
                player.hand.addToHand(card);

            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            player.hand.refreshHandLayout();
        }

        this.isDone = true;
    }
}