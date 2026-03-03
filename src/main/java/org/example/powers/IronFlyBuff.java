package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.monsters.IronBird;

public class IronFlyBuff extends FlightPower {
    public static final String POWER_ID = "Liver:IronFly";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/IronFly128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/IronFly48.png";

    public IronFlyBuff(AbstractCreature owner, int amount) {
        super(owner, amount);
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {

        if(owner instanceof IronBird){
            if(((IronBird)owner).intent!= AbstractMonster.Intent.ATTACK)
                return 0;

            else if(info.type== DamageInfo.DamageType.NORMAL){
                addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,1)));
                if(((IronBird) owner).nextMove==(byte)1){
                    ((IronBird) owner).changeIntent((byte)0);

                }
                return super.onAttacked(info, damageAmount);
            }
            return damageAmount;
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0]+this.description;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (owner instanceof IronBird) {
            ((IronBird) owner).changeIntent((byte) 5);

        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, StrengthPower.POWER_ID));
    }
}
