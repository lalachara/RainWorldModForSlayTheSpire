package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.RedCat;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.powers.ChuangBuff;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.Spear_TAG;


public class MimicrySpearPower extends AbstractPower {
    public static final String POWER_ID = "RedCat:MimicrySpear";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/CatComing128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/CatComing48.png";


    public MimicrySpearPower(AbstractCreature owner, int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(Corrupt_Tag)&&card.type== AbstractCard.CardType.ATTACK)
            card.tags.add(Spear_TAG);
        super.onUseCard(card, action);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(card.hasTag(Corrupt_Tag)&&card.type== AbstractCard.CardType.ATTACK){
            addToBot(new ApplyPowerAction(m,owner,new ChuangBuff(m,amount),amount));
        }

        super.onPlayCard(card, m);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
