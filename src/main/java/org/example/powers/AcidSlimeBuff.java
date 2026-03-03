package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class AcidSlimeBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:AcidSlimeBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/AcidSlime128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/AcidSlime48.png";


    public AcidSlimeBuff(AbstractCreature owner, int amount) {
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner&&owner!=null&&owner.currentBlock!=0) {
            flash();
            addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, amount*owner.currentBlock/2, DamageInfo.DamageType.THORNS)));
            addToBot(new MakeTempCardInDiscardAction(new Slimed(), amount));
        }

        return damageAmount;
    }

//    public void atStartOfTurn() {
//        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
