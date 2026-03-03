package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class ExhaustAllCorruptAction extends AbstractGameAction {
    private boolean iscorrupted;
    private int damage;
    public ExhaustAllCorruptAction(boolean iscorrupted, int damage) {
        super();
        this.damage = damage;
        this.iscorrupted = iscorrupted;

    }

    public void update() {
//        // 遍历抽牌堆
//        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
//            if (c.hasTag(Corrupt_Tag)!=iscorrupted) {
//                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
//                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AttackEffect.FIRE));
//            }
//        }
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(Corrupt_Tag)!=iscorrupted) {
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AttackEffect.FIRE));

            }
        }
//        // 遍历弃牌堆
//        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
//            if (c.hasTag(Corrupt_Tag)!=iscorrupted) {
//                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
//                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AttackEffect.FIRE));
//            }
//        }


        this.isDone = true;
    }
}