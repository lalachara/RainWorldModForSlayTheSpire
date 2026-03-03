package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;


public class Liver_GrassJuice extends CustomRelic {
    public static final String ID = "Liver:GrassJuice";
    private static final String IMG_PATH = "images/CharacterImg/Relics/juice.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/juice.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_GrassJuice() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

}