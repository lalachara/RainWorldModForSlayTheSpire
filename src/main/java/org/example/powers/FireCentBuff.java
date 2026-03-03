package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.HandDrill;
import jdk.internal.foreign.CABI;
import org.example.Achievement.AchievementMgr;
import org.example.monsters.FireCent;


public class FireCentBuff extends AbstractPower {
    public static final String POWER_ID = "Rainworld:FireCentBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/FireCent128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/FireCent48.png";
    int count = 0;
    boolean hasblock = true;
    public FireCentBuff(AbstractCreature owner, int amount) {
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
        if(owner.currentBlock<=amount){
            addToBot(new GainBlockAction(owner,owner,amount-owner.currentBlock));
        }
        hasblock = true;
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(owner.currentBlock==0&&hasblock&&owner instanceof FireCent){
            hasblock = false;
            switch (count){
                case 0:
                    ((FireCent) owner).state.setAnimation(1, "1hit", false);
                    break;
                case 1:
                    ((FireCent) owner).state.setAnimation(1, "2hit", false);
                    break;
                case 2:
                    ((FireCent) owner).state.setAnimation(1, "3hit", false);
                    break;
                case 3:
                    addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
                    AchievementMgr.unlockAchievement(33);
                    break;
            }
            count++;

        }

        return damageAmount;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if(owner instanceof FireCent){
            ((FireCent) owner).state.setAnimation(1, "4hit", false);
        }
    }
    //    public void atStartOfTurn() {
//        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1]+(4-count)+DESCRIPTIONS[2];
    }
}
