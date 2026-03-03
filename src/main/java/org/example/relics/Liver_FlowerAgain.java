package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;


public class Liver_FlowerAgain extends CustomRelic {
    public static final String ID = "Liver:FlowerAgain";
    private static final String IMG_PATH = "images/CharacterImg/null.png";
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_FlowerAgain() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        counter = 1;
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        // super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        /* 33 */     return this.DESCRIPTIONS[0];
        /*    */   }
    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

    @Override
    public void atBattleStart() {
        counter = 1;
    }
}
