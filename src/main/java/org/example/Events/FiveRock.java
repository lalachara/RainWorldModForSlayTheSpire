package org.example.Events;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import org.example.Character.*;
import org.example.relics.Liver_TalkSign;


public class FiveRock extends AbstractImageEvent {
    public static final String ID = "Liver:FiveRock";
    private static final String IMG = "images/CharacterImg/Events/Rock.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    int EventStage = 0;

    public FiveRock() {
        super(NAME, DESCRIPTIONS[0], IMG);

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);


    }

    @Override
    protected void buttonEffect(int i) {
        this.imageEventText.clearAllDialogs();
        switch (EventStage){
            case 0:
            {

                switch (i) {
                    case 0:
                        if(AbstractDungeon.player instanceof SlugCat){
                            SlugCat p = (SlugCat) AbstractDungeon.player;
                            p.lossWorkLevel();
                        }else
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,new DamageInfo(AbstractDungeon.player,9999, DamageInfo.DamageType.NORMAL)));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/Rock1.png");
                        this.imageEventText.setDialogOption(OPTIONS[2]);
                        EventStage = 1;
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/Rock.png");
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        EventStage = 3;
                        break;

                }
                break;
            }
            case 1:
            {
                if(AbstractDungeon.player instanceof Liver){
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.setDialogOption(OPTIONS[3],new Liver_TalkSign());
                    this.imageEventText.loadImage("images/CharacterImg/Events/Rock2.png");
                }
                else{
                    this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                    this.imageEventText.setDialogOption(OPTIONS[5]);
                    this.imageEventText.loadImage("images/CharacterImg/Events/Rock2Red.png");

                }

                EventStage = 2;
                break;
            }
            case 2:
            {
                if(AbstractDungeon.player instanceof Liver){
                    this.imageEventText.loadImage("images/CharacterImg/Events/Rock3.png");
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                }

                else{
                    this.imageEventText.loadImage("images/CharacterImg/Events/Rock3Red.png");
                    this.imageEventText.updateBodyText(DESCRIPTIONS[6]);
                }

                this.imageEventText.setDialogOption(OPTIONS[4]);

                EventStage = 3;
                if(AbstractDungeon.player instanceof Liver)
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Liver_TalkSign());
                else if(AbstractDungeon.player instanceof SlugCat){
                    SlugCat p = (SlugCat) AbstractDungeon.player;
                    p.addMaxWorkLevel(1);
                }

                break;
            }
            case 3:
                openMap();
                break;

        }

    }
}
