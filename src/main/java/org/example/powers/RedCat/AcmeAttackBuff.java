package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;


public class AcmeAttackBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:AcmeAttackBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/DontTouch128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/DontTouch48.png";


    public AcmeAttackBuff(AbstractCreature owner, int amount) {
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

//    @Override
//    public void onExhaust(AbstractCard card) {
//        flash(); // 播放能力触发的闪光效果
//        addToBot(new ApplyPowerAction(owner,owner,new VigorPower(owner,amount),amount));
//    }
//    @Override
//    public void atStartOfTurn() {
//        super.atStartOfTurn();
//        addToBot(
//                new ApplyPowerAction(
//                        owner,
//                        owner,
//                        new VigorPower(owner, amount),
//                        amount
//                )
//        );
//    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner!=null && !info.owner.isPlayer&&owner.hasPower(RedMushroomBuff.POWER_ID)){
            flash(); // 播放能力触发的闪光效果
            addToBot(new ApplyPowerAction(info.owner,owner,new ConstrictedPower(info.owner,owner,amount),amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(target!=null && !target.isPlayer&&owner.hasPower(RedMushroomBuff.POWER_ID)){
            flash(); // 播放能力触发的闪光效果
            addToBot(new ApplyPowerAction(target,owner,new ConstrictedPower(target,owner,amount),amount));
        }
        super.onAttack(info, damageAmount, target);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +amount+ DESCRIPTIONS[1];
    }
}
