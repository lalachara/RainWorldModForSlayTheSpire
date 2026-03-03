package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.monsters.MushRoom;

public class ProliferationBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:Proliferation";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/MushRoom128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/MushRoom48.png";


    public ProliferationBuff(AbstractCreature owner, int amount) {
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
        if(owner instanceof MushRoom){
            if(((MushRoom)owner).isSleep)
                addToBot(new HealAction(owner,owner,AbstractDungeon.ascensionLevel >= 9?100:80));

        }

    }

    @Override
    public void onExhaust(AbstractCard card) {

        owner.maxHealth += amount;
        owner.healthBarUpdatedEvent();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, 1), 1));
        addToBot(new HealAction(owner,owner,amount));

    }


    @Override
    public int onHeal(int healAmount) {
        if(healAmount+owner.currentHealth>owner.maxHealth)
            {
                owner.maxHealth = healAmount+owner.currentHealth;
                owner.healthBarUpdatedEvent();
        }
        return super.onHeal(healAmount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount!=0&&owner instanceof MushRoom){
            if(((MushRoom)owner).isSleep){
                ((MushRoom)owner).wake();

            }


        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
