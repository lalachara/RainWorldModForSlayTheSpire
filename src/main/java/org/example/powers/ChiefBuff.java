package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Actions.ChiefAction;
import org.example.Actions.ChiefCostAction;

import static org.example.Character.SlugCat.Enums.Treasure_TAG;


public class ChiefBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:ChiefBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Chief128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Chief48.png";


    public ChiefBuff(AbstractCreature owner, int amount) {
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
        for (int i = 0; i < amount; i++) {
            addToBot(new ChiefAction());
        }
        addToBot(new ChiefCostAction());
    }

    @Override
    public void onInitialApplication() {
        addToBot(new ChiefCostAction());
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.hasTag(Treasure_TAG)&&card.costForTurn > 0) {

            card.setCostForTurn(0);
            card.cost = 0;
            card.isCostModified = true;

        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
