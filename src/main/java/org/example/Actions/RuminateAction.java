package org.example.Actions;


import com.badlogic.gdx.utils.Timer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.nextRoomTransitionStart;

public class RuminateAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterToHandAction")).TEXT;
    private final AbstractPlayer player;

    private final boolean optional = false;

    public RuminateAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;

    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.discardPile.isEmpty() ) {
                AbstractCard c;
                Iterator var6;
                if (this.player.discardPile.size() == 1 && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    var6 = this.player.discardPile.group.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        cardsToMove.add(c);
                    }

                    var6 = cardsToMove.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (this.player.hand.size() == 10) {
                            this.player.discardPile.moveToDiscardPile(c);
                            this.player.createHandIsFullDialog();
                        } else {
                            AbstractCard finalC = c;
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    finalC.setCostForTurn(finalC.costForTurn - 1);
                                    AbstractDungeon.player.discardPile.moveToHand(finalC, AbstractDungeon.player.discardPile);
                                    System.out.println("执行减费："+ finalC.name+"cost： "+ finalC.costForTurn);
                                }
                            }, 0.5f);

                        }

                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    var6 = this.player.discardPile.group.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        temp.addToTop(c);
                        //c.setCostForTurn(c.costForTurn - 1);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);

                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, 1, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false);
                        }


                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (this.player.hand.size() == 10) {
                        this.player.discardPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                    } else {
                        this.player.discardPile.moveToHand(c, this.player.discardPile);
                    }
                    c.setCostForTurn(c.costForTurn - 1);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }


}

