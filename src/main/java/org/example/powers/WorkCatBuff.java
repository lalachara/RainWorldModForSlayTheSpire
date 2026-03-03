package org.example.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import static org.example.Character.SlugCat.Enums.Treasure_TAG;


public class WorkCatBuff extends AbstractPower {
    public static final String POWER_ID = "Liver:WorkCatBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Money128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Money48.png";
    public WorkCatBuff(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.description = DESCRIPTIONS[0];
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);


        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    // 核心：修改卡牌可用性检查

    // 可选：在打出状态牌时触发效果

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(usedCard.hasTag(Treasure_TAG)) {
            getGold();
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if(card.hasTag(Treasure_TAG)) {
            getGold();
        }
    }

    public void getGold() {
        flash();
        // 这里可以添加打出状态牌时的额外效果
        for (int i = 0; i < amount; i++) {
            AbstractDungeon.effectList.add(
                    new GainPennyEffect( AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)
            );
        }
        addToBot(new GainGoldAction(amount));

    }
}