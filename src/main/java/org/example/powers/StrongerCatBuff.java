package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.cards.Liver.attacks.Liver_Strike;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.CatStrike_Tag;

public class StrongerCatBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:StrongerCatBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Strong128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Strong48.png";


    public StrongerCatBuff(AbstractCreature owner, int amount) {
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
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card.hasTag(CatStrike_Tag)){
            return damage+amount*damage;
        }

        return super.atDamageGive(damage, type, card);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(CatStrike_Tag)) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
        }
    }

    @Override
    public void onSpecificTrigger() {

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
