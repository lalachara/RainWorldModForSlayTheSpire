package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.example.cards.Liver.skills.Liver_Fruit;


public class Liver_RelicFruit2 extends CustomRelic {
    public static final String ID = "Liver:RelicFruit2";
    private static final String IMG_PATH = "images/CharacterImg/Relics/fruit2.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/fruit2.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;


    public Liver_RelicFruit2() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
        RelicStrings relicStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getRelicStrings(ID);
        this.description = relicStrings.DESCRIPTIONS[0];
        initializeTips();

        //如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除

    }

    public void obtain() {
        updateDescription(AbstractDungeon.player.chosenClass);
        if (AbstractDungeon.player.hasRelic(Liver_RelicFruit.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(Liver_RelicFruit.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
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
        /* 31 */
        return AbstractDungeon.player.hasRelic(Liver_RelicFruit.ID);
        /*    */
    }


    @Override
    public void atBattleStart() {
        super.atBattleStart();
        // 在战斗开始时执行的代码
        AbstractCard card = new Liver_Fruit();
        card.upgrade();
        addToBot(new MakeTempCardInHandAction(card));
    }
//     public int changeNumberOfCardsInReward(int numberOfCards) {
//        /* 26 */     return numberOfCards + 1;
//        /*    */   }


}
