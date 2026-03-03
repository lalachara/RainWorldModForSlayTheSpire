package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;


public class Liver_LongLegMushroom extends CustomRelic {
    public static final String ID = "Liver:LongLegMushroom";
    private static final String IMG_PATH = "images/CharacterImg/Relics/mushroom.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/mushroom.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_LongLegMushroom() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);

        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        // super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return this.DESCRIPTIONS[0];

    }

    @Override
    public void atTurnStart() {
        if(AbstractDungeon.player instanceof SlugCat) {
            SlugCat player = (SlugCat) AbstractDungeon.player;
            if(player.food==player.maxFood)
                addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }
}