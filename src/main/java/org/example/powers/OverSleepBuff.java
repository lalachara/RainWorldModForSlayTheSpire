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
import org.example.Character.*;
import org.example.relics.Liver_SleepPotions;


public class OverSleepBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:OverSleepBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/null.png";
    private static final String IMG_48 = "images/CharacterImg/null.png";



    public OverSleepBuff(AbstractCreature owner, int amount) {
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

        if(AbstractDungeon.player instanceof SlugCat){;
            SlugCat p = (SlugCat)AbstractDungeon.player;
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new StrengthPower(this.owner,amount),amount));
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(this.owner,amount),amount));
            p.heal(p.maxHealth);
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {

        if(damageAmount!=0&&!AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID)){
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
            return damageAmount;
        }
        return damageAmount;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
