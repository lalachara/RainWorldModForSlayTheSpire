package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.example.Character.SlugCat;

import java.util.Objects;


public class Metabolize extends AbstractPower {
    public static final String POWER_ID = "RedCat:Metabolize";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    //public static boolean skipNext = false; // 静态标记
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Twohands128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Twohands48.png";

    public Metabolize(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(
                new ApplyPowerAction(
                        owner,
                        owner,
                        new VigorPower(owner, amount),
                        amount
                )
        );
//        if(owner instanceof SlugCat) {
//            SlugCat slugCat = (SlugCat) owner;
//            if (slugCat.food > slugCat.sleepfood) {
//                addToBot(
//                        new ApplyPowerAction(
//                                slugCat,
//                                slugCat,
//                                new VigorPower(slugCat, amount),
//                                amount
//                        )
//                );
//            }
//        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0]+amount;
    }



}