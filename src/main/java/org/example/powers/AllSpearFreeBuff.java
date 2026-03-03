package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Actions.FreeSpearAction;
import org.example.Actions.ResetCostAction;



public class AllSpearFreeBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:AllSpearFreeBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/FreeSpear128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/FreeSpear48.png";


    public AllSpearFreeBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.description = DESCRIPTIONS[0];


        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new FreeSpearAction());
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new FreeSpearAction());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount--;
        if(amount<=0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
            AbstractDungeon.actionManager.addToBottom(new ResetCostAction());
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new FreeSpearAction());

    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
