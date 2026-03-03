package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.cards.Liver.attacks.Liver_Chase;

import java.util.Objects;

public class ChaseCostUpdateAction extends AbstractGameAction {
        public void update() {
            // 遍历手牌
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (Objects.equals(c.cardID, Liver_Chase.ID)) {
                    c.setCostForTurn(c.costForTurn-1);
                }
            }
            // 遍历抽牌堆
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (Objects.equals(c.cardID, Liver_Chase.ID)) {
                    c.setCostForTurn(c.costForTurn-1);
                }
            }
            // 遍历弃牌堆
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (Objects.equals(c.cardID, Liver_Chase.ID)) {
                    c.setCostForTurn(c.costForTurn-1);
                }
            }
            // 遍历暂时牌堆
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (Objects.equals(c.cardID, Liver_Chase.ID)) {
                    c.setCostForTurn(c.costForTurn-1);
                }
            }
            this.isDone = true;
        }
    }