package org.example.powers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.relics.Liver_GrassJuice;


public class ChuangBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:ChuangBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/ChuangImg128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/ChuangImg48.png";
    private boolean isAttack = false; // 用于记录攻击次数

    public ChuangBuff(AbstractCreature owner, int amount) {
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
    public void atStartOfTurn() {
        isAttack = false;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
            if(target!=null&&target!=owner&&info.type==DamageInfo.DamageType.NORMAL){
                //this.amount+=1;
                if(AbstractDungeon.player.hasRelic(Liver_GrassJuice.ID)&&owner!= AbstractDungeon.player)
                    this.amount++;
                isAttack = true;
                this.updateDescription();
                AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, new DamageInfo(owner, amount, DamageInfo.DamageType.NORMAL), this.amount));

            }


    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(!isAttack&&!AbstractDungeon.player.hasRelic(Liver_GrassJuice.ID)) {
            if (this.amount > 1 ) {
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
        this.description = DESCRIPTIONS[0];
    }
}
