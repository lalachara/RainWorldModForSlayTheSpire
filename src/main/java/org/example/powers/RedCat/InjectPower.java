package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import org.example.Actions.AddToBotAction;
import org.example.powers.ChuangBuff;
import org.example.tools.Tools;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.Spear_TAG;


public class InjectPower extends AbstractPower {
    public static final String POWER_ID = "RedCat:InjectPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/CatComing128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/CatComing48.png";


    public InjectPower(AbstractCreature owner, int amount) {
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
    /*    */   public void onUseCard(AbstractCard card, UseCardAction action) {
        /* 41 */     if (card.type== AbstractCard.CardType.ATTACK||card.type== AbstractCard.CardType.SKILL&&!card.purgeOnUse && this.amount > 0) {
            /* 42 */       flash();
                            Tools.CorruptCard(card);
            /* 43 */       AbstractMonster m = null;
            /*    */
            /* 45 */       if (action.target != null) {
                /* 46 */         m = (AbstractMonster)action.target;
                /*    */       }
            /*    */
            /* 49 */       AbstractCard tmp = card.makeSameInstanceOf();
            /* 50 */       AbstractDungeon.player.limbo.addToBottom(tmp);
            /* 51 */       tmp.current_x = card.current_x;
            /* 52 */       tmp.current_y = card.current_y;
            /* 53 */       tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            /* 54 */       tmp.target_y = Settings.HEIGHT / 2.0F;
            /* 55 */       if (m != null) {
                /* 56 */         tmp.calculateCardDamage(m);
                /*    */       }
            /*    */
            /* 59 */       tmp.purgeOnUse = true;
            /* 60 */       AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            /*    */
            /*    */
            /*    */
            /* 64 */       this.amount--;
            /* 65 */       if (this.amount == 0) {
                /* 66 */         addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                /*    */       }
                        addToBot(new AddToBotAction(new  ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile)));
            /*    */     }
        /*    */   }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+ DESCRIPTIONS[1]; ;
    }
}
