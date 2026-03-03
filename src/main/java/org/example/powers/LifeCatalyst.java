package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.skills.RedCat_Corrupt;


public class LifeCatalyst extends AbstractPower {
    public static final String POWER_ID = "RedCat:LifeCatalyst";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Because128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Because48.png";

    public LifeCatalyst(AbstractCreature owner, int amount) {
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

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new MakeTempCardInHandAction(new RedCat_Corrupt(), amount));
        addToBot(new DrawCardAction(amount*3));
        if(AbstractDungeon.player instanceof RedCat) {
            RedCat RC = (RedCat) AbstractDungeon.player;
            RC.addCorrupt(1);
        }
        super.atStartOfTurnPostDraw();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
