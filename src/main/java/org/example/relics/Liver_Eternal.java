package org.example.relics;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.cards.Liver.attacks.Liver_ThreeAgree;
import org.example.cards.Liver.powers.Liver_WorkError;
import org.example.cards.Liver.skills.Liver_TwistFate;

import java.util.Objects;


public class Liver_Eternal extends CustomRelic {
    public static final String ID = "Liver:Eternal";
    private static final String IMG_PATH = "images/CharacterImg/Relics/Eternal.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/Eternal.png";
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;
    public Liver_Eternal() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {

    }

    @Override
    public void onEquip() {
        AchievementMgr.unlockAchievement(6);
       if(AbstractDungeon.player instanceof SlugCat)
           ((SlugCat)AbstractDungeon.player).maxWorkLevel = 9;
        for (int i = 0; i < AbstractDungeon.player.masterDeck.size(); i++) {
            if(Objects.equals(AbstractDungeon.player.masterDeck.group.get(i).cardID, Liver_ThreeAgree.ID)
                    || Objects.equals(AbstractDungeon.player.masterDeck.group.get(i).cardID, Liver_TwistFate.ID)
                    || Objects.equals(AbstractDungeon.player.masterDeck.group.get(i).cardID, Liver_WorkError.ID)
            ){
                ((CustomCard) AbstractDungeon.player.masterDeck.group.get(i)).bannerSmallRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special.png"));
                ((CustomCard) AbstractDungeon.player.masterDeck.group.get(i)).bannerLargeRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special_L.png"));
               AbstractDungeon.player.masterDeck.group.get(i).initializeDescription();
            }
        }
    }
    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

}