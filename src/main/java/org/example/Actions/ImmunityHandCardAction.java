package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.*;

public class ImmunityHandCardAction extends AbstractGameAction {

    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {

            if (!c.hasTag(Corrupt_Tag)&&(c.type== AbstractCard.CardType.ATTACK||c.type== AbstractCard.CardType.SKILL)) {
                c.tags.add(Immunity_Tag);
                if(!c.hasTag(CantCorrupt_Tag))
                    c.tags.add(CantCorrupt_Tag);
            }
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        this.isDone = true;
    }
}