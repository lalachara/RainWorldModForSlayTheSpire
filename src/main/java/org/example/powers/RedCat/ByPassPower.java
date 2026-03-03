package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.example.Actions.AddToBotAction;
import org.example.Character.SlugCat;
import org.example.powers.Inherent;
import org.example.tools.Tools;


public class ByPassPower extends AbstractPower {
    public static final String POWER_ID = "RedCat:ByPassPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/Bypass_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/ByPass_48.png";

    public ByPassPower(AbstractCreature owner, int amount) {
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
    public void onVictory() {
        if(owner.hasPower(Inherent.POWER_ID)){
            int count = ((Inherent)owner.getPower(Inherent.POWER_ID)).turnCount;
            if(count<3){
                if(owner instanceof SlugCat){
                   ((SlugCat) owner).addWorkLevel(amount);
                }
                addRandomRelicToReward(this.amount);
            }

        }
        super.onVictory();
    }


  public static void addRandomRelicToReward(int amount) {
      for (int i = 0; i < amount; i++) {
            AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
       AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
            AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
            }
   }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }
}
