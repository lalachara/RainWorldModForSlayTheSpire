package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.GremlinHorn;
import org.example.Actions.ReturnHandAction;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.powers.HuntSignBuff;


public class RedCat_Menu extends CustomRelic {
    public static final String ID = "RedCat:Menu";
    private static final String IMG_PATH = "images/CharacterImg/Relics/Menu.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/Menu.png";
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;




    public RedCat_Menu() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void atBattleStart() {
        addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new HuntSignBuff(AbstractDungeon.player, 2), 2));
        super.atBattleStart();
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
        if(this.counter>3){
            this.counter = 0;
            this.flash();
            if(AbstractDungeon.player instanceof SlugCat){
                ((SlugCat) AbstractDungeon.player).addWorkLevel(1);
            }
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof RedCat;
    }

}