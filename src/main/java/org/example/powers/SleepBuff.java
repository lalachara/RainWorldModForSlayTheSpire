package org.example.powers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.powers.RedCat.RedMushroomBuff;
import org.example.relics.Liver_SleepPotions;


public class SleepBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:SleepBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/SleepBuff128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/SleepBuff48.png";

    public SleepBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];
        this.priority = 99;


        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }
    @Override
    public void atStartOfTurn() {
        if(AbstractDungeon.player instanceof SlugCat) {
            SlugCat p = (SlugCat)AbstractDungeon.player;
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(this.owner,amount),amount));
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(this.owner,amount),amount));
            p.addWorkLevel(amount);
            if(p.hasPower(RedMushroomBuff.POWER_ID)&&p.hasPower(CantControl.POWER_ID)){
                ((RedCat)p).setCorruptAnimation();
            }else
                p.setidleAnimation();
        }
        if(AbstractDungeon.player instanceof RedCat){
            //AbstractDungeon.actionManager.addToTop(new CorruptMotivateAction());
            if(owner.hasPower(RedMushroomBuff.POWER_ID)){
                ((RedMushroomBuff)owner.getPower(RedMushroomBuff.POWER_ID)).successfulexit = true;

                ((RedCat)AbstractDungeon.player).quitbianyi();
                ((RedCat)AbstractDungeon.player).addCorrupt(-1*((RedCat)AbstractDungeon.player).var);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
    }



    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount!=0&&!AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID)){
            //AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
//            if(owner.hasPower(RedMushroomBuff.POWER_ID)&& AbstractDungeon.player instanceof RedCat){
//                ((RedCat)AbstractDungeon.player).quitbianyi();
//            }
//            if(owner instanceof SlugCat){
//                SlugCat p = (SlugCat)AbstractDungeon.player;
//                p.setidleAnimation();
//            }
            return damageAmount;
        }

        return damageAmount;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
