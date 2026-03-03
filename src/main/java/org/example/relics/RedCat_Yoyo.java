package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.Actions.ReturnHandAction;
import org.example.Character.RedCat;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.skills.RedCat_CorruptBlock;
import org.example.powers.RedCat.BackToHandPower;

import java.util.ArrayList;


public class RedCat_Yoyo extends CustomRelic {
    public static final String ID = "RedCat:Yoyo";
    private static final String IMG_PATH = "images/CharacterImg/Relics/yoyo.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/yoyo.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;
    private boolean used =false;



    public RedCat_Yoyo() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        used = false;
        super.atBattleStart();
    }

    @Override
    public void atTurnStart() {
        used = false;
        super.atTurnStart();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if((targetCard.type== AbstractCard.CardType.ATTACK||targetCard.type==AbstractCard.CardType.SKILL) &&!used){
            used = true;
            addToBot(new ReturnHandAction(targetCard));
        }
        super.onUseCard(targetCard, useCardAction);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof RedCat;
    }

}