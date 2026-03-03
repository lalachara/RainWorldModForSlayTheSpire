package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class AllCardInitDescription extends AbstractGameAction {
    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            c.initializeDescription();
        }
        // 遍历抽牌堆
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            c.initializeDescription();
        }
        // 遍历弃牌堆
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            c.initializeDescription();
        }
        // 遍历暂时牌堆
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            c.initializeDescription();
        }
        this.isDone = true;
    }
}