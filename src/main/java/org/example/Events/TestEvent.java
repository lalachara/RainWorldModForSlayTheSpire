package org.example.Events;


/*     */ import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
/*     */ import com.megacrit.cardcrawl.core.CardCrawlGame;
/*     */ import com.megacrit.cardcrawl.core.Settings;
/*     */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/*     */ import com.megacrit.cardcrawl.events.AbstractEvent;
/*     */
/*     */ import com.megacrit.cardcrawl.localization.EventStrings;
/*     */
/*     */
/*     */
/*     */
/*     */ import com.megacrit.cardcrawl.rooms.AbstractRoom;
/*     */ import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.cards.Liver.skills.Liver_Neurone;
/*     */
/*     */

public class TestEvent extends AbstractEvent {
    public static final String ID = "Liver:Test";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private CurScreen screen;
    public TestEvent() {
        super();
             this.screen = CurScreen.INTRO;
             this.body = DESCRIPTIONS[0];
             this.roomEventText.addDialogOption(OPTIONS[0]);
            this.roomEventText.addDialogOption(OPTIONS[1], (AbstractCard)new Liver_Neurone());
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.EVENT;
            this.hasDialog = true;
             this.hasFocus = true;


    }
            @Override
/*     */   protected void buttonEffect(int i) {
    /*  49 */     switch (this.screen) {
        /*     */       case INTRO:
            /*  51 */         if (i == 0) {
                /*  52 */           this.screen = CurScreen.FIGHT;
                /*  53 */           this.roomEventText.updateBodyText(DESCRIPTIONS[0]);
                /*  54 */           this.roomEventText.clearRemainingOptions();
                /*  55 */           this.roomEventText.updateDialogOption(0, OPTIONS[0]);
                            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(1));

                            break;

                            /*     */         }
            /*  57 */         if (i == 1) {
                /*  58 */           this.screen = CurScreen.END;
                /*  59 */           this.roomEventText.updateBodyText(DESCRIPTIONS[0]);
                /*  60 */           this.roomEventText.clearRemainingOptions();
                /*  61 */           this.roomEventText.updateDialogOption(0, OPTIONS[1]);
                /*     */           AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player,AbstractDungeon.player, 9999));
                /*  62 */           AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard)new Liver_Neurone(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                /*     */         }
            /*     */         break;
        /*     */       case FIGHT:
            /*  66 */         AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25, 35));
            /*  67 */         enterCombat();
            /*     */         break;
        /*     */       case END:
            /*  70 */         openMap();
            /*     */         break;
        /*     */     }openMap();
    /*     */   }
    /*     */   private enum CurScreen
            /*     */   {
        /*  91 */     INTRO,
        /*  92 */     FIGHT,
        /*  93 */     END;
        /*     */   }
    /*     */
}
