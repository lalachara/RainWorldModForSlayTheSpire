package org.example.powers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.*;
import org.example.relics.RedCat_Menu;


public class HuntSignBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:HuntSignBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Huntbuff128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Huntbuff48.png";


    public HuntSignBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(AbstractDungeon.player instanceof SlugCat&&damageAmount!=0&&info.type== DamageInfo.DamageType.NORMAL&&info.owner==AbstractDungeon.player) {
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            liver.addFood(1);
        }
        return damageAmount;
    }

//    @Override
//    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
//
//            if(AbstractDungeon.player instanceof SlugCat&&damage!=0&&card!=null) {
//                SlugCat liver = (SlugCat) AbstractDungeon.player;
//                liver.addFood(1);
//                }
//
//        return damage;
//    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount > 1) {
            this.amount--;
            this.updateDescription();
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
            );
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
