package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static org.example.Character.SlugCat.Enums.Spear_TAG;


public class CatComingBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:CatComingBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/CatComing128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/CatComing48.png";


    public CatComingBuff(AbstractCreature owner, int amount) {
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

    }
    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(Spear_TAG))
            addToBot(new DrawCardAction( amount));
    }

    @Override
    public void onSpecificTrigger() {
//        addToBot(new DrawCardAction(2*amount));
//        if(AbstractDungeon.player instanceof SlugCat) {
//            SlugCat liver = (SlugCat) AbstractDungeon.player;
//            liver.addFood(2 * amount);
//        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
