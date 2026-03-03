package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpearShapeAction extends AbstractGameAction {
    private AbstractCard c;
    public SpearShapeAction(AbstractCard card) {
        this.c = card;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = 0.1F; // 设置持续时间
    }
    @Override
    public void update() {


        AbstractDungeon.player.discardPile.moveToHand(c, AbstractDungeon.player.discardPile);
        addToBot(new ReduceCostForTurnAction(c, 99)); // 将卡牌的费用减少1
        isDone = true; // 设置动作完成
    }
}
