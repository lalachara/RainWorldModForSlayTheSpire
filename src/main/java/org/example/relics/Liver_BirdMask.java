package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;
import org.example.powers.NimbleBuff;


public class Liver_BirdMask extends CustomRelic {
    public static final String ID = "Liver:BirdMask";
    private static final String IMG_PATH = "images/CharacterImg/Relics/birdmask.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/birdmask.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_BirdMask() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);

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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount==0)
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new NimbleBuff(AbstractDungeon.player,1),1));
        return super.onAttacked( info,  damageAmount);
    }

    @Override
    public void atBattleStart() {
        counter = 1;
    }
}
