package org.example.Events;

import com.evacipated.cardcrawl.mod.stslib.RelicTools;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.BigFish;
import com.megacrit.cardcrawl.events.shrines.Nloth;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;
import org.example.cards.Liver.others.Liver_HeartFear;
import org.example.cards.Liver.skills.Liver_Neurone;
import org.example.relics.Liver_MoonSkin;
import org.example.relics.Liver_TalkSign;
import org.example.relics.RedCat_Key;
import org.example.tools.Tools;

public class MeetMoonRedCat extends AbstractImageEvent {
    public static final String ID = "RedCat:Moon";
    private static final String IMG = "images/CharacterImg/Events/RedMoon1.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    boolean hasSelect = false;
    int state = 0;
    public MeetMoonRedCat() {

        super(NAME, AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)?DESCRIPTIONS[1]:DESCRIPTIONS[0], IMG);
        if(AbstractDungeon.player.hasRelic(RedCat_Key.ID)){
            this.imageEventText.setDialogOption(OPTIONS[0],new RedCat_Key());
        }else {
            this.imageEventText.setDialogOption(OPTIONS[4],true);
        }

        this.imageEventText.setDialogOption(OPTIONS[3]);
        //this.imageEventText.setDialogOption(OPTIONS[2],new Liver_HeartFear());
    }

    @Override
    protected void buttonEffect(int i) {
        switch (state){
            case 0:
                this.imageEventText.clearAllDialogs();
                switch (i){
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.setDialogOption(OPTIONS[1]);
                        this.imageEventText.setDialogOption(OPTIONS[2],new Liver_HeartFear());
                        this.imageEventText.loadImage("images/CharacterImg/Events/RedMoon2.png");
                        AbstractDungeon.player.loseRelic((RedCat_Key.ID));
                        state = 1;
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/RedMoon3.png");
                        if (!CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()) {
                            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[6], true, false, false, false);}
                        state = 2;
                        return;
                }
                break;
            case 1:
                this.imageEventText.clearAllDialogs();
                switch (i){
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic( AbstractRelic.RelicTier.RARE);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
                        state = 2;
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/Moon3.png");
                        AbstractDungeon.player.maxHealth += 5;
                        if(AbstractDungeon.player instanceof SlugCat)
                        {
                            ((SlugCat)AbstractDungeon.player).addFood(999);
                        }
                        AbstractDungeon.player.heal(999);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_HeartFear(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                        AchievementMgr.unlockAchievement(28);
                        state = 2;
                        return;
                }
                break;
            case 2:
                openMap();
                break;
            default:
                openMap();
                break;

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
