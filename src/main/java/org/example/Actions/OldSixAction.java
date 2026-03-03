package org.example.Actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class OldSixAction extends AbstractGameAction {
    @Override
    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type== AbstractCard.CardType.POWER|| c.type== AbstractCard.CardType.SKILL) {
                c.setCostForTurn(c.costForTurn-1);
            } 
        }
//        // 遍历抽牌堆
//        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
//            if (c.type!= AbstractCard.CardType.ATTACK) {
//                c.setCostForTurn(c.costForTurn-1);
//            }
//        }
//        // 遍历弃牌堆
//        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
//            if (c.type!= AbstractCard.CardType.ATTACK) {
//                c.setCostForTurn(c.costForTurn-1);
//            }
//        }
//        // 遍历暂时牌堆
//        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
//            if (c.type!= AbstractCard.CardType.ATTACK) {
//                c.setCostForTurn(c.costForTurn-1);
//            }
//        }
        this.isDone = true;
    }

}
