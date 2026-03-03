package org.example.Actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustPermanentlyAction extends AbstractGameAction {
    private final AbstractCard card;

    public ExhaustPermanentlyAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    public void update() {
        if (this.isDone) return;

        // 1. 从当前区域移除卡牌
        removeCardFromCurrentZone();

        // 2. 从主卡组永久移除
        removeFromMasterDeck();

        // 3. 添加视觉特效
        //addExhaustEffect();

        // 4. 添加到移除卡牌列表（可选）
       // addToRemovedCards();

        this.isDone = true;
    }

    private void removeCardFromCurrentZone() {
        AbstractPlayer p = AbstractDungeon.player;

        // 检查所有可能的区域
        if (p.hand.contains(card)) {
            p.hand.removeCard(card);
        } else if (p.drawPile.contains(card)) {
            p.drawPile.removeCard(card);
        } else if (p.discardPile.contains(card)) {
            p.discardPile.removeCard(card);
        } else if (p.exhaustPile.contains(card)) {
            p.exhaustPile.removeCard(card);
        } else if (p.limbo.contains(card)) {
            p.limbo.removeCard(card);
        }
    }

    private void removeFromMasterDeck() {
        AbstractPlayer p = AbstractDungeon.player;

        // 从主卡组移除所有同名卡（包括升级版）
        p.masterDeck.group.removeIf(c ->
                c.cardID.equals(card.cardID) && c.uuid.equals(card.uuid)
        );
    }

    private void addExhaustEffect() {
        // 创建独特的消失特效
        //AbstractDungeon.effectList.add(new PermanentExhaustEffect(card));
        CardCrawlGame.sound.play("CARD_EXHAUST");
    }

}