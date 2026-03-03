package org.example.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.example.powers.WorkLevelLockBuff;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class WorkFlower extends AbstractPotion {

    public static final String POTION_ID = "Liver:WorkFlower";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;


    public WorkFlower() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.JAR, AbstractPotion.PotionColor.NONE);
        // 设置药水的效果、描述等
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", loadImage("images/CharacterImg/potions/workflower_body.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidImg", loadImage("images/CharacterImg/potions/workflower_liquid.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "spotsImg", loadImage("images/CharacterImg/potions/workflower_slot.png"));

        liquidColor = Color.WHITE.cpy();
        spotsColor = Color.WHITE.cpy();
        labOutlineColor = Color.WHITE.cpy();
        this.isThrown = false; // 如果是投掷型药水，设置为 true
        this.targetRequired = false; // 如果不需要目标，设置为 false
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WorkLevelLockBuff(AbstractDungeon.player,potency),potency));
    }
    public void initializeData() {
            this.potency = getPotency();
            this.description = potionStrings.DESCRIPTIONS[0]+ this.potency + potionStrings.DESCRIPTIONS[1];
            this.tips.add(new PowerTip(this.name, this.description));
            this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
//            this.tips.clear();
//            this.tips.add(new PowerTip(this.name, this.description));
//            this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
           }
    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new WorkFlower();
    }

}
