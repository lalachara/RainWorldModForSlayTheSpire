package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;



public class Liver_MoonSkin extends CustomRelic {
    public static final String ID = "Liver:MoonSkin";
    private static final String IMG_PATH = "images/CharacterImg/Relics/MoonSkin.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/MoonSkin.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;
    public Liver_MoonSkin() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);

        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
    }

    @Override
    public void onEquip() {
        AchievementMgr.unlockAchievement(7);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {

    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

}