

package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import org.example.Actions.ReturnHandAction;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.powers.CorruptStrengthPower;

import java.util.ArrayList;
import java.util.Objects;

public class RedMushroomBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:RedMushroomBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public boolean successfulexit = false;
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/RedMushRoom_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/RedMushRoom_48.png";


    public RedMushroomBuff(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F)));
        ArrayList<AbstractCard> discardPile = (ArrayList<AbstractCard>) AbstractDungeon.player.discardPile.group.clone();
        for (int i = 0; i < discardPile.size(); i++) {
            if(Objects.equals(discardPile.get(i).cardID, RedCat_CorruptStrike.ID)) {
                addToBot(new ReturnHandAction(discardPile.get(i)));
            }
        }
        ArrayList<AbstractCard> exhaustPile = (ArrayList<AbstractCard>) AbstractDungeon.player.exhaustPile.group.clone();{
            for (int i = 0; i < exhaustPile.size(); i++) {
                if(Objects.equals(exhaustPile.get(i).cardID, RedCat_Corrupt.ID)) {
                    addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new CorruptStrengthPower(AbstractDungeon.player,1),2));
                }
            }
        }



//        if(owner.currentBlock!=0)
//        {
//            int temp = owner.currentBlock;
//            addToBot(new RemoveAllBlockAction(owner,owner));
//            addToBot(new AddTemporaryHPAction(owner,owner,temp));
//        }
//        addToBot(new AddTemporaryHPAction(owner,owner,10));
//        if(owner.hasPower(StrengthPower.POWER_ID)){
//            int strengthamount = owner.getPower(StrengthPower.POWER_ID).amount;
//            addToBot(new RemoveSpecificPowerAction(owner,owner,StrengthPower.POWER_ID));
//            addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,strengthamount),strengthamount));
//        }
//        if(owner.hasPower(VigorPower.POWER_ID)){
//            int vigoramount = owner.getPower(VigorPower.POWER_ID).amount+2;
//            addToBot(new RemoveSpecificPowerAction(owner,owner,VigorPower.POWER_ID));
//            addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,vigoramount/3),vigoramount/3));
//        }
//        if(owner.hasPower(LoseStrengthPower.POWER_ID)){
//            int losestrengthamount = owner.getPower(LoseStrengthPower.POWER_ID).amount;
//            if(losestrengthamount>1){
//                addToBot(new RemoveSpecificPowerAction(owner,owner,LoseStrengthPower.POWER_ID));
//                addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,-losestrengthamount/2),-losestrengthamount/2));
//            }
//
//        }
        addToBot(new AddTemporaryHPAction(owner,owner,10));
        //addToBot(new MakeTempCardInHandAction(new RedCat_Corrupt(),2));
        super.onInitialApplication();
    }


//    @Override
//    public void onGainedBlock(float blockAmount) {
//
//        addToBot(new RemoveAllBlockAction(owner,owner));
//        addToBot(new AddTemporaryHPAction(owner,owner, (int) blockAmount));
//
//    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        super.onApplyPower(power, target, source);
//        if(target==owner){
//            if(power.ID.equals(StrengthPower.POWER_ID)){
//                addToBot(new RemoveSpecificPowerAction(owner,owner,StrengthPower.POWER_ID));
//                addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,power.amount),power.amount));
//
//            }else if(power.ID.equals(LoseStrengthPower.POWER_ID)){
//                addToBot(new RemoveSpecificPowerAction(owner,owner,LoseStrengthPower.POWER_ID));
//                addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,-power.amount/2),-power.amount/2));
//            }
//            else if(power.ID.equals(VigorPower.POWER_ID)){
//                addToBot(new RemoveSpecificPowerAction(owner,owner,VigorPower.POWER_ID));
//                addToBot(new ApplyPowerAction(owner,owner,new CorruptStrengthPower(owner,power.amount/3),power.amount/3));
//            }
//        }
//    }
    //    @Override
//    public int onPlayerGainedBlock(int blockAmount) {
//        addToBot(new AddTemporaryHPAction(owner,owner,blockAmount));
//        System.out.println("RedMushroomBuff gained block: " + blockAmount);
//        return 0;
//    }


    @Override
    public void onExhaust(AbstractCard card) {
        addToBot(new AddTemporaryHPAction(owner,owner,2));
        super.onExhaust(card);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if(owner.hasPower(CorruptStrengthPower.POWER_ID)&&!owner.hasPower(Inveteracy.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(owner,owner,CorruptStrengthPower.POWER_ID));
        }
        int temphp = TempHPField.tempHp.get(owner);
        addToBot(new RemoveAllTemporaryHPAction(owner,owner));
//        if(successfulexit){
        addToBot(new GainBlockAction(owner,owner,temphp));
//        }else {
        //addToBot(new ApplyPowerAction(owner,owner,new WeakPower(owner,1,false)));
            //AbstractDungeon.player.loseEnergy(999);

//        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters){
//            if(m.hasPower(EatPower.POWER_ID))
//                addToBot(new RemoveSpecificPowerAction(m,owner,EatPower.POWER_ID));
//        }

    }
}
