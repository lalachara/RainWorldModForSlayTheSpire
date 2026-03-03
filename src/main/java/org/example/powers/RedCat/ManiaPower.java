package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.example.Character.SlugCat;
import org.example.powers.Inherent;


public class ManiaPower extends AbstractPower {
    public static final String POWER_ID = "RedCat:ManiaPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/ManiaPower_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/ManiaPower_48.png";


    public ManiaPower(AbstractCreature owner, int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type== AbstractCard.CardType.ATTACK)
            addToBot(new ApplyPowerAction(owner,owner,new VigorPower(owner,amount),amount));
        super.onUseCard(card, action);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if(card.type== AbstractCard.CardType.SKILL){
            return false;
        }
        return super.canPlayCard(card);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction( AbstractDungeon.player,AbstractDungeon.player,this.ID));
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
