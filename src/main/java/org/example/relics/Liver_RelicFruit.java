package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.example.Character.*;
import org.example.cards.Liver.skills.Liver_Fruit;


public class Liver_RelicFruit extends CustomRelic {
    public static final String ID = "Liver:RelicFruit";
    private static final String IMG_PATH = "images/CharacterImg/Relics/fruit1.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/fruit1.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;


    public Liver_RelicFruit() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(IMG_OL_PATH),RELIC_TIER, LANDING_SOUND);
        RelicStrings relicStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getRelicStrings(ID);
        this.description = relicStrings.DESCRIPTIONS[0];
        initializeTips();

         //如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除

    }
    @Override
    public void initializeTips() {
        super.initializeTips();
        RelicStrings relicStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getRelicStrings(ID);
        this.tips.clear();
        this.tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(this.name, relicStrings.DESCRIPTIONS[0]));
    }
    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }
    @Override
    public void atBattleStart() {
        super.atBattleStart();
        // 在战斗开始时执行的代码
        addToBot(new MakeTempCardInHandAction(new Liver_Fruit()));
    }
//     public int changeNumberOfCardsInReward(int numberOfCards) {
//        /* 26 */     return numberOfCards + 1;
//        /*    */   }


}
