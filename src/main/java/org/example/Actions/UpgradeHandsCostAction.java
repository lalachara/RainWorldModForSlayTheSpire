package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;

public class UpgradeHandsCostAction extends AbstractGameAction {


    public UpgradeHandsCostAction() {
        super();


    }

    public void update() {
        // 遍历抽牌堆
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.hasTag(Corrupt_Tag) && card.costForTurn > 0) {
                card.isCostModified = true;
                card.costForTurn = card.costForTurn - 1;
                card.superFlash();
            }
        }

        this.isDone = true;
    }
}