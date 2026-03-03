package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.example.tools.Tools;

import java.util.ArrayList;
import java.util.Iterator;

import static org.example.Character.SlugCat.Enums.CantCorrupt_Tag;
import static org.example.Character.SlugCat.Enums.Corrupt_Tag;

public class SelectHandCardCorrupt
           extends AbstractGameAction
         {
   private final AbstractPlayer p;
   private static final UIStrings uiStrings;
   public static final String[] TEXT;
   //private boolean isRandom;

  // private int amount;
    /*  23 */   private final ArrayList<AbstractCard> etherealCards = new ArrayList<>();

   public SelectHandCardCorrupt() {
        /*  26 */     this.p = AbstractDungeon.player;
        /*  28 */     this.actionType = AbstractGameAction.ActionType.SPECIAL;
        /*  29 */     //this.amount = amount;
        /*  29 */
        /*  30 */     this.duration = Settings.ACTION_DUR_FAST;
           }

   public void update() {
        /*  34 */     if (this.p.hand.isEmpty()) {
            /*  35 */       this.isDone = true;
            
                   return;
                 }
        /*  39 */     Iterator<AbstractCard> var1 = this.p.hand.group.iterator();
        
        /*  41 */     if (this.duration == Settings.ACTION_DUR_FAST) {
            /*  42 */       while (var1.hasNext()) {
                /*  43 */         AbstractCard c = var1.next();
                /*  44 */         if (c.hasTag(Corrupt_Tag)||c.hasTag(CantCorrupt_Tag)||c.type== AbstractCard.CardType.CURSE||c.type== AbstractCard.CardType.STATUS) {
                    /*  45 */           this.etherealCards.add(c);
                             }
                       }
            
            /*  49 */       if (this.etherealCards.size() == this.p.hand.group.size()) {
                                addToBot(new UpgradeHandsCostAction());
                /*  50 */        this.isDone = true;
                
                         return;
                       }
            /*  54 */       this.p.hand.group.removeAll(this.etherealCards);
            /*  55 */       if(p.hand.group.size()==1){
                AbstractCard c = p.hand.getTopCard();
                c.superFlash();
                Tools.CorruptCard(c);
                returnCards();
               addToBot(new UpgradeHandsCostAction());
                this.isDone = true;

                return;
           }

            /*  61 */       {
                /*  62 */         AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                /*  63 */         tickDuration();
                
                         return;
                       }

                 }
        
        
        /*  77 */     if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            /*  78 */       var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            
            /*  80 */       while (var1.hasNext()) {
                /*  81 */         AbstractCard c = var1.next();
                /*  82 */         c.superFlash();
                /*  83 */         Tools.CorruptCard(c);
                /*  84 */         this.p.hand.addToTop(c);
                       }
            returnCards();
            addToBot(new UpgradeHandsCostAction());

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
              AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
              this.isDone = true;
              return;
                 }
        
        
           tickDuration();
           }





   private void returnCards() {
        for (AbstractCard c : this.etherealCards) {
     this.p.hand.addToTop(c);
                 }
           this.p.hand.refreshHandLayout();
           }

   static {
             uiStrings = CardCrawlGame.languagePack.getUIString("RedCat:ChooseCardToCorrupt");
            TEXT = uiStrings.TEXT;
           }
 }
