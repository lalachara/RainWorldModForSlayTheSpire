package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;

import java.util.Objects;


public class Liver_Tubularis extends CustomRelic {
    public static final String ID = "Liver:Tubularis";
    private static final String IMG_PATH = "images/CharacterImg/Relics/xiguan.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/xiguan.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public Liver_Tubularis() {
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
    public void onEquip() {
        AbstractDungeon.player.masterDeck.addToTop(new Slimed());
        AbstractDungeon.player.masterDeck.addToTop(new Slimed());
  }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(Objects.equals(drawnCard.cardID, Slimed.ID))
            drawnCard.modifyCostForCombat(-3);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        if(card.cardID.equals(Slimed.ID)&&AbstractDungeon.player instanceof SlugCat) {
           SlugCat player = (SlugCat) AbstractDungeon.player;
           player.addFood(1);
        }
    }

}
