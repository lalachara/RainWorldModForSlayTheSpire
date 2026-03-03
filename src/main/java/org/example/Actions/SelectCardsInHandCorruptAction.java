package org.example.Actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static org.example.Character.SlugCat.Enums.CantCorrupt_Tag;
import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.tools.Tools.CorruptCard;

public class SelectCardsInHandCorruptAction extends AbstractGameAction {
    private final AbstractPlayer player;


    public SelectCardsInHandCorruptAction() {
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer player = AbstractDungeon.player;

            // 创建过滤后的卡组（排除已腐蚀卡牌）
            CardGroup selectableCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : player.hand.group) {
                if (!card.hasTag(Corrupt_Tag)&& !card.hasTag(CantCorrupt_Tag)&&card.type!= AbstractCard.CardType.CURSE&&card.type!= AbstractCard.CardType.STATUS) {
                    selectableCards.addToTop(card);
                    System.out.println("可腐化卡牌：" + card.name);
                }
            }

            // 检查是否有可腐蚀的卡牌
            if (selectableCards.size() > 0) {
                AbstractDungeon.handCardSelectScreen.open("选择一张牌腐蚀", 1, false, false);
                this.tickDuration();
                return;
            } else {
                this.isDone = true;
                return;
            }
        }

        // 处理玩家选择
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                // 添加腐蚀标签
                if(card.cost>=0){
                    card.isCostModified = card.cost>0;
                    card.costForTurn = 0;
                }

                //card.cost = 0;
                CorruptCard(card);
                // 将卡牌放回手牌
                player.hand.addToTop(card);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
        }
}