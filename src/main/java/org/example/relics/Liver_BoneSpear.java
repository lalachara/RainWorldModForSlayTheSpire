package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;

public class Liver_BoneSpear extends CustomRelic {
    public static final String ID = "Liver:BoneSpear";
    private static final String IMG_PATH = "images/CharacterImg/Relics/bonespear.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/bonespear.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_BoneSpear() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);

        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
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