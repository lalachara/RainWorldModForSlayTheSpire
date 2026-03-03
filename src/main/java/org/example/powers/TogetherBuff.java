package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;


public class TogetherBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:TogetherBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Combo128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Combo48.png";
    private int skills=0,atks=0;

    public TogetherBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];


        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        skills = 0;
        atks = 0;
    }
    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if(card.type == AbstractCard.CardType.SKILL)
        { if(atks>0){
                atks=0;
                applyPower();
            }
            else
                skills++;}
        if(card.type== AbstractCard.CardType.ATTACK){
            { if(skills>0){
                skills=0;
                applyPower();
            }
            else
                atks++;}
        }
    }

    public void applyPower(){
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));

    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
