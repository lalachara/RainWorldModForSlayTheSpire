package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import org.example.powers.NimbleBuff;

import java.util.Objects;

public class ExhaustSlimeAction extends AbstractGameAction {
    @Override
    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            event(c);
        }
        // 遍历抽牌堆
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            event(c);
        }
        // 遍历弃牌堆
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            event(c);
        }
        // 遍历暂时牌堆
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            event(c);
        }
        this.isDone = true;
    }
    public void event(AbstractCard c) {
        if(Objects.equals(c.cardID, Slimed.ID)) {
            // 如果是Slime卡牌，则将其移除
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 1), 1));
        }
    }
}
