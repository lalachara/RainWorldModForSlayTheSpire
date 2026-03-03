package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.attacks.RedCat_SpearMelee;

import java.util.ArrayList;
import java.util.Objects;

public class GetSpearMeleeAction extends AbstractGameAction {


    public GetSpearMeleeAction() {

        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update() {

        ArrayList<AbstractCard> discardPile = (ArrayList<AbstractCard>) AbstractDungeon.player.discardPile.group.clone();
        for (int i = 0; i < discardPile.size(); i++) {
            // 获取一张矛牌
            if(Objects.equals(discardPile.get(i).cardID, RedCat_SpearMelee.ID)) {
                // 将矛牌移到手牌
                if(discardPile.get(i) instanceof RedCat_SpearMelee){
                    RedCat_SpearMelee spearMelee = (RedCat_SpearMelee) discardPile.get(i);
                    if(spearMelee.isused)
                        addToBot(new ReturnHandAction(discardPile.get(i)));
                }

            }

        }
        this.isDone = true; // 动作完成
    }
}
