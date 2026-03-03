package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import org.example.Actions.OldSixAction;


public class OldSixBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:OldSixBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Oldsix128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Oldsix48.png";


    public OldSixBuff(AbstractCreature owner, int amount) {
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
    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount > 1) {
            this.amount--;
            this.updateDescription();
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
            );
        }
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new OldSixAction());

    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card.type == AbstractCard.CardType.ATTACK){
            damage*= 1+amount;
        }
        return damage;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.type== AbstractCard.CardType.POWER|| card.type== AbstractCard.CardType.SKILL) {
            card.setCostForTurn(card.costForTurn - 1);
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) { // 检查是否是攻击牌
            if (this.owner.hasPower(IntangiblePlayerPower.POWER_ID)) { // 检查是否有无实体状态
                int intangibleAmount = this.owner.getPower(IntangiblePlayerPower.POWER_ID).amount;

                if (intangibleAmount - this.amount <= 0) {
                    // 如果剩余层数小于等于 0，直接移除无实体 Buff
                    AbstractDungeon.actionManager.addToBottom(
                            new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, IntangiblePlayerPower.POWER_ID)
                    );
                } else {
                    // 否则减少无实体层数
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(this.owner, this.owner,
                                    new com.megacrit.cardcrawl.powers.IntangiblePlayerPower(this.owner, -this.amount), -this.amount)
                    );
                }
            }

            // 移除该能力
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
            );
        }
    }
}
