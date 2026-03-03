package org.example.Events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.cards.Liver.others.Liver_HeartFear;
import org.example.cards.Liver.skills.Liver_Neurone;
import org.example.relics.Liver_MoonSkin;
import org.example.relics.Liver_TalkSign;
import org.example.tools.Tools;

public class MeetMoon extends AbstractImageEvent {
    public static final String ID = "Liver:Moon";
    private static final String IMG = "images/CharacterImg/Events/Moon.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    boolean hasSelect = false;

    public MeetMoon() {

        super(NAME, AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)?DESCRIPTIONS[1]:DESCRIPTIONS[0], IMG);
        if(Tools.hasCard(Liver_Neurone.ID)){
            this.imageEventText.setDialogOption(OPTIONS[0],new Liver_Neurone(),new Liver_MoonSkin());
        }else {
            this.imageEventText.setDialogOption(OPTIONS[3],true);
        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2],new Liver_HeartFear());
    }

    @Override
    protected void buttonEffect(int i) {
            if (!this.hasSelect) {
                 this.hasSelect = true;
                 this.imageEventText.clearAllDialogs();
                 this.imageEventText.setDialogOption(OPTIONS[4]);

                 switch (i) {
                   case 0:
                        this.imageEventText.updateBodyText(AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)?DESCRIPTIONS[2]:DESCRIPTIONS[5]);
                       this.imageEventText.loadImage("images/CharacterImg/Events/Moon2.png");
                        AbstractDungeon.player.masterDeck.removeCard(Liver_Neurone.ID);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Liver_MoonSkin());

                        break;
                   case 1:
                        this.imageEventText.updateBodyText(AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)?DESCRIPTIONS[3]:DESCRIPTIONS[6]);
                       this.imageEventText.loadImage("images/CharacterImg/Events/Moon1.png");
                       if (!CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()) {
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[5], true, false, false, false);}
                        break;
                   case 2:
                        this.imageEventText.updateBodyText(AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)?DESCRIPTIONS[4]:DESCRIPTIONS[7]);
                       this.imageEventText.loadImage("images/CharacterImg/Events/Moon3.png");
                        AbstractDungeon.player.maxHealth += 5;
                        if(AbstractDungeon.player instanceof SlugCat)
                        {
                            ((SlugCat)AbstractDungeon.player).addFood(999);
                        }
                        AbstractDungeon.player.heal(999);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_HeartFear(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                        AchievementMgr.unlockAchievement(28);
                       break;
                 }

          } else {

                openMap();
           }
    }
    /*    */   public void update() {
        /* 47 */     super.update();
        /* 48 */     if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            /* 49 */       AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            /* 50 */       c.upgrade();
            /* 51 */       logMetricCardUpgrade("Upgrade Shrine", "Upgraded", c);
            /* 52 */       AbstractDungeon.player.bottledCardUpgradeCheck(c);
            /* 53 */       AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
            /* 54 */       AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            /* 55 */       AbstractDungeon.gridSelectScreen.selectedCards.clear();
            /*    */     }
        /*    */   }
    /*    */
}
