package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.example.Character.*;


public class Liver_BatteryEnergy extends CustomRelic {
    public static final String ID = "Liver:BatteryEnergy";
    private static final String IMG_PATH = "images/CharacterImg/Relics/bettery.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/bettery.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;
    public Liver_BatteryEnergy() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(IMG_OL_PATH),RELIC_TIER, LANDING_SOUND);
        counter = 0;
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        // super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount>0){
            counter++;
            if (counter>=8&&!usedUp) {
                usedUp();
                CardCrawlGame.sound.play("Liver:PointBoom");
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,40, DamageInfo.DamageType.HP_LOSS,AbstractGameAction.AttackEffect.FIRE));
                addToBot(new DamageAction(AbstractDungeon.player,new DamageInfo(AbstractDungeon.player,40,DamageInfo.DamageType.HP_LOSS)));

            }
        }


        return super.onAttacked(info, damageAmount);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

}