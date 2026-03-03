package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Actions.ReturnHandAction;
import org.example.Character.RedCat;


public class RedCat_Key extends CustomRelic {
    public static final String ID = "RedCat:RecoverKey";
    private static final String IMG_PATH = "images/CharacterImg/Relics/key.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/key.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;
    private boolean used =false;



    public RedCat_Key() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof RedCat;
    }

}