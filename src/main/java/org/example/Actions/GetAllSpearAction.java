package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class GetAllSpearAction  extends AbstractGameAction {
    public void update() {
        int amount = AbstractDungeon.player.masterHandSize; // 获取玩家的手牌上限
        // 遍历抽牌堆
        ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(Spear_TAG)) {
                cardsToMove.add(c);
                amount--;
                if(amount <= 0) {
                    break; // 如果已经达到手牌上限，停止添加
                }
            }
        }

        for (AbstractCard c : cardsToMove) {
            // 将符合条件的卡牌从抽牌堆移动到手牌
            AbstractDungeon.player.drawPile.moveToHand(c, AbstractDungeon.player.drawPile);
        }

        this.isDone = true;
    }
}