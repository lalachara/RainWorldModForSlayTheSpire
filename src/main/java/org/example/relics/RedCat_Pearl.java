package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.Character.RedCat;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.skills.RedCat_CorruptBlock;

import java.util.ArrayList;


public class RedCat_Pearl extends CustomRelic {
    public static final String ID = "RedCat:Pearl";
    private static final String IMG_PATH = "images/CharacterImg/Relics/board.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/board.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;



    public RedCat_Pearl() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
            counter = 0;
    }

    @Override
    public void setCounter(int counter) {
        this.counter+=counter;
        while (this.counter>=10){
            this.counter-=10;
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof RedCat;
    }

}