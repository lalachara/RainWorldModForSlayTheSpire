package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;


public class UnbalanceBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:UnbalanceBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    //public static boolean skipNext = false; // 静态标记
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Twohands128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Twohands48.png";

    public UnbalanceBuff(AbstractCreature owner, int amount) {
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
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if( info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner.isPlayer) {
            return damageAmount * 2; // 双倍伤害
        }
        return damageAmount;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return super.atDamageReceive(damage, damageType)*2;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.amount > 1 ) {
            this.amount--;
            this.updateDescription();
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
            );
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {

            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
            );

        super.atStartOfTurn();
    }
}