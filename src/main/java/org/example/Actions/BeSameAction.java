package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;

public class BeSameAction extends AbstractGameAction {
    private boolean iscorrupted;

    public BeSameAction(boolean iscorrupted) {
        super();
        this.iscorrupted = iscorrupted;

    }

    public void update() {
        // 遍历抽牌堆
        int amount = 0;
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {

            if (c.hasTag(Corrupt_Tag)!=iscorrupted) {
                addToBot(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));

                amount++;

            }
        }

        addToBot(new DrawCardAction(amount));

        this.isDone = true;
    }
}