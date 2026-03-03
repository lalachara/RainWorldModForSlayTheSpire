package org.example.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.example.powers.ChuangBuff;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class Chuang extends AbstractPotion {

    public static final String POTION_ID = "Liver:Chuang";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;


    public Chuang() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, null);
        // 设置药水的效果、描述等
        this.isThrown = true; // 如果是投掷型药水，设置为 true
        this.targetRequired = true; // 如果不需要目标，设置为 false
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", loadImage("images/CharacterImg/potions/chuang.png"));
        labOutlineColor = Color.WHITE.cpy();
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        addToBot(new ApplyPowerAction(abstractCreature,abstractCreature,new ChuangBuff(abstractCreature,potency),potency));
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
        return 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Chuang();
    }
}
