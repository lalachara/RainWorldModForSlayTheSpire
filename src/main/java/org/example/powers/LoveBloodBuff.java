package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.monsters.RedCabrite;


public class LoveBloodBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:LoveBlood";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/Fire128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Fire48.png";
    boolean added = false;

    public LoveBloodBuff(AbstractCreature owner, int amount) {
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
        super.atStartOfTurn();
        added = false;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.type==PowerType.DEBUFF)
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2), 2));
    }

//    @Override
//    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
//        super.onAttack(info, damageAmount, target);
//        if(owner instanceof RedCabrite){
//            if(damageAmount!=0){
//                ((RedCabrite) owner).isAtk =true;
//                AbstractDungeon.actionManager.addToBottom(new RollMoveAction((AbstractMonster) owner));
//                if(!added) {
//                    added = true;
//                    addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2)));
//                }
//
//            }else {
//                ((RedCabrite) owner).isAtk =false;
//            }
//        }
//    }
//    public void atStartOfTurn() {
//        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(owner instanceof RedCabrite) {
            owner.state.setAnimation(1, "behit", false);
            owner.state.addAnimation(0, "idle", true,0.0F);
        }


        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
