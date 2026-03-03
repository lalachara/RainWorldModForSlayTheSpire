package org.example.Events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.Character.*;
import org.example.cards.Liver.others.Liver_stubborn;


public class FruitTree extends AbstractImageEvent {
    public static final String ID = "Liver:FruitTree";
    private static final String IMG = "images/CharacterImg/Events/FruitTree.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    int EventStage = 0;

    public FruitTree() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.imageEventText.setDialogOption(OPTIONS[0],new Liver_stubborn());
        this.imageEventText.setDialogOption(OPTIONS[1]+1+OPTIONS[2]+30+OPTIONS[5]);
    }

    @Override
    protected void buttonEffect(int i) {
        this.imageEventText.clearAllDialogs();
        if(EventStage==0){
            switch (i) {
                case 0:
                    if(AbstractDungeon.player instanceof SlugCat){
                        SlugCat p = (SlugCat) AbstractDungeon.player;
                        p.addFood(2);
                        if(p.workLevel<p.maxWorkLevel-1)
                            p.addWorkLevel(1);
                    }
                    this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree2.png");
                    this.imageEventText.setDialogOption(OPTIONS[4]);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_stubborn(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                    EventStage = -1;
                    break;
                case 1:
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                    AbstractDungeon.player.damage(new DamageInfo(null, MathUtils.ceil(EventStage+1), DamageInfo.DamageType.HP_LOSS));
                    int random = AbstractDungeon.cardRandomRng.random(9);
                    if(random<=3){
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree.png");
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        EventStage = -1;
                    }

                    else{
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree.png");
                        EventStage++;
                        this.imageEventText.setDialogOption(OPTIONS[1]+(EventStage+1)+OPTIONS[2]+(EventStage+3)+0+OPTIONS[5]);
                        this.imageEventText.setDialogOption(OPTIONS[3],new Liver_stubborn());
                    }
                    break;

            }
        } else if (EventStage == -1) {
            openMap();
        }else{
            if(i==0){
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                AbstractDungeon.player.damage(new DamageInfo(null, MathUtils.ceil(EventStage+1), DamageInfo.DamageType.HP_LOSS));
                int random = AbstractDungeon.cardRandomRng.random(9);
                if(random<=3+EventStage){
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree.png");
                    this.imageEventText.setDialogOption(OPTIONS[4]);
                    EventStage = -1;
                }
                else{
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree.png");
                    EventStage++;
                    this.imageEventText.setDialogOption(OPTIONS[1]+(EventStage+1)+OPTIONS[2]+(EventStage+3)+0+OPTIONS[5]);
                    this.imageEventText.setDialogOption(OPTIONS[3],new Liver_stubborn());
                }

            }else {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_stubborn(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));

                this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                this.imageEventText.loadImage("images/CharacterImg/Events/FruitTree.png");
                this.imageEventText.setDialogOption(OPTIONS[4]);
                EventStage = -1;
            }

        }


    }
}
