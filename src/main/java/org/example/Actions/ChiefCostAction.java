package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.Treasure_TAG;


public class ChiefCostAction extends AbstractGameAction {
    @Override
    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(Treasure_TAG)&&c.cost>0) {
                c.cost = 0;
                c.setCostForTurn(0);
            }
        }
        // 遍历抽牌堆
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(Treasure_TAG)&&c.cost>0) {
                c.cost = 0;
                c.setCostForTurn(0);
            }
        }
        // 遍历弃牌堆
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(Treasure_TAG)&&c.cost>0) {
                c.cost = 0;
                c.setCostForTurn(0);
            }
        }
        // 遍历暂时牌堆
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(Treasure_TAG)&&c.cost>0) {
                c.cost = 0;
                c.setCostForTurn(0);
            }
        }
        this.isDone = true;
    }

}
