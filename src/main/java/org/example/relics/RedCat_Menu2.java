package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.powers.HuntSignBuff;


public class RedCat_Menu2 extends CustomRelic {
    public static final String ID = "RedCat:Menu2";
    private static final String IMG_PATH = "images/CharacterImg/Relics/Menu2.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/Menu.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;




    public RedCat_Menu2() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void atBattleStart() {
        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
            if(!AbstractDungeon.getMonsters().monsters.get(i).isDeadOrEscaped()){
                addToBot(new ApplyPowerAction(AbstractDungeon.getMonsters().monsters.get(i),AbstractDungeon.player, new HuntSignBuff(AbstractDungeon.player, 3), 3));
            }
        }
        super.atBattleStart();
    }

    public void obtain() {
        updateDescription(AbstractDungeon.player.chosenClass);
        if (AbstractDungeon.player.hasRelic(RedCat_Menu.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if ((AbstractDungeon.player.relics.get(i)).relicId.equals(RedCat_Menu.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void onEquip() {
        this.counter = 0;
        super.onEquip();
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0) {
            if(m.hasPower(HuntSignBuff.POWER_ID)){
                setCounter(1);
                this.flash();
            }
        }

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void setCounter(int counter) {

        this.counter+=1;
        if(this.counter>2){
            this.counter = 0;
            this.flash();
            if(AbstractDungeon.player instanceof SlugCat){
                ((SlugCat) AbstractDungeon.player).addWorkLevel(1);
            }
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(RedCat_Menu.ID);
    }

}