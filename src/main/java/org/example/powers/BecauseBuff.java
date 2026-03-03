package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.*;
import org.example.relics.Liver_Eternal;


public class BecauseBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:Because";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Because128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Because48.png";


    public BecauseBuff(AbstractCreature owner, int amount) {
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
    public void onInitialApplication() {

    }

    public void atStartOfTurn() {
        if(owner instanceof SlugCat){
            SlugCat liver = (SlugCat) owner;
            if(liver.hasRelic(Liver_Eternal.ID))
                liver.addWorkLevel(1);
            else if(liver.workLevel > 0) {
                liver.lossWorkLevel();
                addToBot(new HealAction(owner, owner, owner.maxHealth));
            }else{
                addToBot(new DamageAction(owner,new DamageInfo(owner,65535, DamageInfo.DamageType.HP_LOSS)));
            }
        }
    }

    @Override
    public void updateDescription() {
        if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID))
            this.description = DESCRIPTIONS[1] ;
        else
            this.description = DESCRIPTIONS[0] ;
    }
}
