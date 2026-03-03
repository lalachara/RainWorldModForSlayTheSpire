package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.powers.RedCat.MimicrySpearPower;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class FreeSpearAction extends AbstractGameAction {
    public void update() {
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(Spear_TAG)||(AbstractDungeon.player.hasPower(MimicrySpearPower.POWER_ID)&&c.hasTag(Corrupt_Tag)&&c.type== AbstractCard.CardType.ATTACK)) {
                c.setCostForTurn(0);
            }
        }
        // 遍历抽牌堆
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.hasTag(Spear_TAG)||(AbstractDungeon.player.hasPower(MimicrySpearPower.POWER_ID)&&c.hasTag(Corrupt_Tag)&&c.type== AbstractCard.CardType.ATTACK)) {
                c.setCostForTurn(0);
            }
        }
        // 遍历弃牌堆
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.hasTag(Spear_TAG)||(AbstractDungeon.player.hasPower(MimicrySpearPower.POWER_ID)&&c.hasTag(Corrupt_Tag)&&c.type== AbstractCard.CardType.ATTACK)) {
                c.setCostForTurn(0);
            }
        }
        // 遍历暂时牌堆
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.hasTag(Spear_TAG)||(AbstractDungeon.player.hasPower(MimicrySpearPower.POWER_ID)&&c.hasTag(Corrupt_Tag)&&c.type== AbstractCard.CardType.ATTACK)) {
                c.setCostForTurn(0);
            }
        }
        this.isDone = true;
    }
}