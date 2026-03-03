package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;


public class MisfortunePower extends AbstractPower {
    public static final String POWER_ID = "RedCat:MisfortunePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/Misfortune_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/Misfortune_48.png";


    public MisfortunePower(AbstractCreature owner, int amount) {
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
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if(card.hasTag(Corrupt_Tag)) {
            return blockAmount * (1+amount); // Reduce block by 50% if the card has the Corrupt tag
        }
        else
            return super.modifyBlock(blockAmount, card);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card.hasTag(Corrupt_Tag)) {
            return damage * (1+amount); // Reduce block by 50% if the card has the Corrupt tag
        }
        else
            return super.atDamageFinalGive(damage, type, card);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
