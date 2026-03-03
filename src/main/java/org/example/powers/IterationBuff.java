package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Actions.IterationAction;

import java.util.Objects;


public class IterationBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:IterationBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Huntbuff128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Huntbuff48.png";


    public IterationBuff(AbstractCreature owner, int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        boolean haveSameCard = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if(Objects.equals(c.cardID, card.cardID)){
                haveSameCard = true;
                break;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(Objects.equals(c.cardID, card.cardID)){
                haveSameCard = true;
                break;
            }
        }

        if(haveSameCard) {
            action.exhaustCard = true;
            this.flash();
            addToBot(new IterationAction(card));
            if (this.amount > 1) {
                this.amount--;
                this.updateDescription();
            } else {
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
                );
            }

        }

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount +"层";
    }
}
