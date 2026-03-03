package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Actions.ReturnHandAction;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;


public class RedCat_mushroom extends CustomRelic {
    public static final String ID = "RedCat:mushroom";
    private static final String IMG_PATH = "images/CharacterImg/Relics/corruptsign.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/corruptsign.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;




    public RedCat_mushroom() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void onEquip() {
        if(AbstractDungeon.player instanceof RedCat){
            ((SlugCat)AbstractDungeon.player).sleepfood =  ((SlugCat)AbstractDungeon.player).maxFood;
        }
        super.onEquip();
    }

    @Override
    public void atBattleStart() {
        if(AbstractDungeon.player instanceof RedCat){
            ((RedCat)AbstractDungeon.player).addCorrupt(5);
            ((SlugCat)AbstractDungeon.player).sleepfood =  ((SlugCat)AbstractDungeon.player).maxFood;
        }

        super.atBattleStart();
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