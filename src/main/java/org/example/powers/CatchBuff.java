package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class CatchBuff extends AbstractPower {
    public static final String POWER_ID = "Rainworld:CatchBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/ThreeChicken128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/ThreeChicken48.png";
    private int increaseTimes=0;

    public CatchBuff(AbstractCreature owner, int amount) {
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
        super.atStartOfTurn();
        this.increaseTimes = 0;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(this.increaseTimes<amount){
            this.increaseTimes++;
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if(damageAmount>0&&target==AbstractDungeon.player){
            AbstractDungeon.player.decreaseMaxHealth(1);
        }
    }
    //    @Override
//    public void onUseCard(AbstractCard card, UseCardAction action) {
//        if(card.type== AbstractCard.CardType.ATTACK||card.type== AbstractCard.CardType.SKILL){
//            if(this.card==null){
//                AbstractDungeon.player.discardPile.removeCard(card);
//                AbstractDungeon.player.hand.removeCard(card);
//                this.card=card;
//            }
//            else {
//                this.addToBot(new MakeTempCardInDiscardAction(this.card, true));
//                AbstractDungeon.player.discardPile.removeCard(card);
//                AbstractDungeon.player.hand.removeCard(card);
//                this.card=card;
//            }
//
//        }
//        this.addToBot(new MakeTempCardInDiscardAction(this.card, true));
//        super.onUseCard(card, action);
//
//    }
    //    public void atStartOfTurn() {
//        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }
}
