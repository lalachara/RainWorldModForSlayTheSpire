package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.cards.Liver.skills.Liver_Fruit;
import org.example.powers.HuntSignBuff;


public class RedCat_RedCatStart extends CustomRelic {
    public static final String ID = "RedCat:RedCatStart";
    private static final String IMG_PATH = "images/CharacterImg/Relics/fruit1.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/fruit1.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;


    public RedCat_RedCatStart() {
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
        return false;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(!AbstractDungeon.player.hasPower(WeakPower.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 1), 1));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseDexterityPower(AbstractDungeon.player, 1), 1));
        }
    }

//    @Override
//    public void atBattleStart() {
//        super.atBattleStart();
//        // 在战斗开始时执行的代码
//        AbstractMonster m = AbstractDungeon.getRandomMonster();
//        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new HuntSignBuff(m,1), 1));
//
//    }
//     public int changeNumberOfCardsInReward(int numberOfCards) {
//        /* 26 */     return numberOfCards + 1;
//        /*    */   }


}
