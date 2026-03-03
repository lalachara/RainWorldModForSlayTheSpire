package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.example.Character.*;

import java.util.ArrayList;
import java.util.List;


public class FatWorldBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:FatWorldBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/FatWorld128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/FatWorld48.png";
    private List<String> cardList;

    public FatWorldBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];
        this.cardList = new ArrayList<>();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }


    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(!cardList.contains(card.cardID)&&owner instanceof SlugCat) {
            cardList.add(card.cardID); // 如果卡牌已经在列表中，则不再添加
            ((SlugCat) owner).addFood(amount);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
