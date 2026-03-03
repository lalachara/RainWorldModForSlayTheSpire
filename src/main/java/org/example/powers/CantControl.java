package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.example.Actions.PlayCardAction;
import org.example.Character.RedCat;
import org.example.cards.RedCat.skills.RedCat_Backflip;
import org.example.cards.RedCat.skills.RedCat_Corrupt;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;


public class CantControl extends AbstractPower {
    public static final String POWER_ID = "RedCat:CantControl";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/UnControl_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/UnControl_48.png";


    public CantControl(AbstractCreature owner, int amount) {
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

//    @Override
//    public void onExhaust(AbstractCard card) {
//        //addToBot(new AddTemporaryHPAction(owner,owner,amount));
//        super.onExhaust(card);
//    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type== AbstractCard.CardType.ATTACK||card.type== AbstractCard.CardType.SKILL){
            addToBot(new DrawCardAction(amount));
            addToBot(new DamageAction(owner,new DamageInfo(owner,amount*2, DamageInfo.DamageType.THORNS)));
        }

        super.onAfterUseCard(card, action);
    }


//    @Override
//    public void onCardDraw(AbstractCard card) {
//        if(card.hasTag(Corrupt_Tag)){
//            addToBot(new PlayCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng),card));
//            addToBot(new DamageAction(owner,new DamageInfo(owner,card.costForTurn, DamageInfo.DamageType.THORNS)));
//            addToBot(new DrawCardAction(1));
//        }
//        super.onCardDraw(card);
//    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1]+2*amount+DESCRIPTIONS[2]; ;
    }
}
