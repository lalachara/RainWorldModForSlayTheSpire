package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;


public class TwoHandsBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:TwoHandsBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    //public static boolean skipNext = false; // 静态标记
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Twohands128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Twohands48.png";

    public TwoHandsBuff(AbstractCreature owner, int amount) {
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
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(source == owner){
            if(Objects.equals(power.ID, ChuangBuff.POWER_ID) &&target!=owner){
                addToBot(new ApplyPowerAction(target,null,new ChuangBuff(target, amount), amount));
            }

            if(power.ID.equals(NimbleBuff.POWER_ID) && target == owner) {
                addToBot(new ApplyPowerAction(owner, null, new NimbleBuff(owner, amount),amount));
            }
        }
    }

//    @Override
//    public void atStartOfTurnPostDraw() {
//        this.flash();
//        this.addToBot(new DrawCardAction(this.owner, amount));
//        this.addToBot(new DiscardAction(this.owner, this.owner, amount, false));
//    }
//    @Override
//    public void onCardDraw(AbstractCard card) {
//        if (skipNext) {
//            skipNext = false;
//            return;
//        }
//        skipNext = true;
//        this.addToBot(new DrawCardAction(this.owner, 1));
//        this.addToBot(new DiscardAction(this.owner, this.owner, 1, false));
//    }

}