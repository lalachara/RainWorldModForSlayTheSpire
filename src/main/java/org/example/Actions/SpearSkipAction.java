package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class SpearSkipAction extends AbstractGameAction {
    AbstractMonster m;
    AbstractCard c;
    int magicNumber;


    public SpearSkipAction(AbstractMonster target,int amount) {
        magicNumber = amount;
        m = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DISCARD;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open("选择一张手牌", 1, false, false);
            tickDuration();
            return;
        }
        CardCrawlGame.dungeon.checkForPactAchievement();

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                player.hand.moveToDiscardPile(card);
                if(m != null &&card.hasTag(Spear_TAG)){
                    addToBot(new ApplyPowerAction(m, player, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
                    if ( !m.hasPower("Artifact")) {
                        addToBot(new ApplyPowerAction(m, player, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
                    }
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            player.hand.refreshHandLayout();
        }

        this.isDone = true;
    }

}
