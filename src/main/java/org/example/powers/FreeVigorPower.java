
package org.example.powers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
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
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.example.Actions.AddToBotAction;
import org.example.cards.RedCat.attacks.RedCat_PullOut;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class FreeVigorPower extends AbstractPower {
    public static final String POWER_ID = "RedCat:FreeVigorPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/QuickRun_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/QuickRun_48.png";


    public FreeVigorPower(AbstractCreature owner, int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type== AbstractCard.CardType.ATTACK&& !Objects.equals(card.cardID, RedCat_PullOut.ID)){
            int sum = owner.getPower(VigorPower.POWER_ID).amount;
            addToBot(new AddToBotAction(new ApplyPowerAction(owner, owner, new VigorPower(owner, sum), sum)));
            amount--;
            if (amount <= 0) {
                addToBot(new AddToBotAction(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, this.ID)));
            } else {
                this.updateDescription();
            }
        }

    }

//    @Override
//    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
//        if(owner.hasPower(VigorPower.POWER_ID)&&card.hasTag(Spear_TAG)){
//            int sum = owner.getPower(VigorPower.POWER_ID).amount;
//            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, sum), sum));
//        }
//        return super.atDamageGive(damage, type, card);
//    }
}
