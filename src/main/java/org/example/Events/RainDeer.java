package org.example.Events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;

import static org.example.Events.NodeTransformer.transformToRest;
import static org.example.Events.NodeTransformer.transformToShop;

public class RainDeer extends AbstractImageEvent {
    public static final String ID = "Liver:RainDeer";
    private static final String IMG = "images/CharacterImg/Events/RainDeer.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    boolean hasSelect = false;
    boolean hasSelect2 = false;

    public RainDeer() {

        super(NAME, DESCRIPTIONS[0], IMG);

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {

        if (!this.hasSelect) {
            this.hasSelect = true;

            switch (i) {
                case 0:
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.loadImage("images/CharacterImg/Events/RainDeer.png");
//                    CardGroup cardGroup = AbstractDungeon.player.masterDeck
//                            .getPurgeableCards();
//                    for (int j = 0; j < cardGroup.size(); j++) {
//                        System.out.println(cardGroup.group.get(j).name);
//                    }
                    if (!CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()) {
                        AbstractDungeon.gridSelectScreen.open(
                                CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                                        .getPurgeableCards()), 1, OPTIONS[2], false);
                    }

                    this.imageEventText.setDialogOption(OPTIONS[4]);
                    this.imageEventText.setDialogOption(OPTIONS[5]);
                    this.imageEventText.setDialogOption(OPTIONS[1]);
                    break;
                case 1:
                    openMap();
                    break;
            }

        } else if (!this.hasSelect2) {
            this.hasSelect2 = true;
            int random = AbstractDungeon.cardRandomRng.random(1);
            if(i==2){
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.loadImage("images/CharacterImg/Events/RainDeer.png");
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.imageEventText.setDialogOption(OPTIONS[3]);

            }
            else if (random==1) {
                this.imageEventText.clearAllDialogs();
                openMap();
                dispose();
                switch (i) {
                    case 0:
                        transformToShop();
                        break;
                    case 1:
                        transformToRest();
                        break;
                }
            } else {
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                this.imageEventText.loadImage("images/CharacterImg/Events/RainDeer1.png");
                this.imageEventText.setDialogOption(OPTIONS[1]);
                if (AbstractDungeon.player instanceof SlugCat) {
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                    SlugCat p = (SlugCat) AbstractDungeon.player;
                    p.lossWorkLevel();
                    if(p.isDead){
                        AchievementMgr.unlockAchievement(30);
                    }
                }
            }
        } else {
            openMap();
        }
    }

    @Override
    public void renderRoomEventPanel(SpriteBatch sb) {
        super.renderRoomEventPanel(sb);
    }

    public void update() {
        super.update();

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractEvent.logMetricCardRemoval("Back to Basics", "Elegance", c);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
        }
    }
}
