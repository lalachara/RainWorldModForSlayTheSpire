package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.SlugCat;
import org.example.relics.Liver_Eternal;



public class BorrowLifeBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:BorrowLifeBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Because128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Because48.png";


    public BorrowLifeBuff(AbstractCreature owner, int amount) {
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
    public void onSpecificTrigger() {
        if(!owner.hasPower(MinusloopPower.POWER_ID)){
            addToBot(new ApplyPowerAction(owner, owner, new MinusloopPower(owner,3),3));
        }
//        amount--;
//        if(amount<=0){
//            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//        }
//        super.onSpecificTrigger();
    }

//    @Override
//    public void onVictory() {
//        if (owner instanceof SlugCat) {
//            SlugCat liver = (SlugCat) owner;
//            for (int i = 0; i < amount; i++) {
//                if (liver.workLevel > 0) {
//                    liver.lossWorkLevel();
//                }
//            }
//
//        }
//    }
    @Override
    public void updateDescription() {
            this.description = DESCRIPTIONS[0] ;
    }
}
