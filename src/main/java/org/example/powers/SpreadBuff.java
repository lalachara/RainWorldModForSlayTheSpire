package org.example.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;
import org.example.tools.Tools;

import javax.tools.Tool;
import java.util.Iterator;
import java.util.Objects;

import static org.example.Character.SlugCat.Enums.*;

public class SpreadBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:SpreadBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Spread128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Spread48.png";


    public SpreadBuff(AbstractCreature owner, int amount) {
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

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if(card.hasTag(Corrupt_Tag)|| Objects.equals(card.cardID, RedCat_Corrupt.ID)){
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();
            while (var1.hasNext()){
                AbstractCard c = (AbstractCard) var1.next();
                if(!c.hasTag(Corrupt_Tag)&&c.type != AbstractCard.CardType.STATUS && c.type != AbstractCard.CardType.CURSE&&!c.hasTag(CantCorrupt_Tag))
                {
                    Tools.CorruptCard(c);
                    return;
                }
            }
            //addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.amount*10, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));

        }
    }
}
