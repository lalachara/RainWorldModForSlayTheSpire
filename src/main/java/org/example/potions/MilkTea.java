package org.example.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.example.Character.*;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class MilkTea extends AbstractPotion {

    public static final String POTION_ID = "Liver:MilkTea";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);


    public static final String NAME = potionStrings.NAME;


    public MilkTea() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, null);
        // 设置药水的效果、描述等
        this.isThrown = false; // 如果是投掷型药水，设置为 true
        this.targetRequired = false; // 如果不需要目标，设置为 false
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", loadImage("images/CharacterImg/potions/milktea.png"));
        labOutlineColor = Color.WHITE.cpy();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if(AbstractDungeon.player instanceof SlugCat){
            ((SlugCat) AbstractDungeon.player).addFood(potency);
        }
    }
    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0]+ this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2],potionStrings.DESCRIPTIONS[3]));

//            this.tips.clear();
//            this.tips.add(new PowerTip(this.name, this.description));
//            this.tips.add(new PowerTip(potionStrings.DESCRIPTIONS[2], potionStrings.DESCRIPTIONS[3]));
    }
    @Override
    public int getPotency(int i) {
        return 4;
    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain) {
            return false;
        }

        return true;
    }
    @Override
    public AbstractPotion makeCopy() {
        return new MilkTea();
    }
}
