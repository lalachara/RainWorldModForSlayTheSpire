package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class BloodyBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:BloodyBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/Bloody_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/Bloody_48.png";


    public BloodyBuff(AbstractCreature owner, int amount) {
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
    public void onInitialApplication() {

    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        return super.atDamageReceive(damage, damageType, card);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if(damageAmount>0){
            damageAmount-=6;
            if(damageAmount<0) damageAmount=0;
            amount--;
            if(amount<=0){
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
                );
            }
            addToBot(new MakeTempCardInDiscardAction(new Wound(), 1));
        }
        return super.onLoseHp(damageAmount);
    }


    @Override
    public void updateDescription() {
            this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1]; ;
    }
}
