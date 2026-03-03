package org.example.patchs;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.shop.ShopScreen;
import org.example.tools.ModConfig;

import static org.example.Achievement.SelectScreenPatch.characters;

@SpirePatch(clz = AbstractDungeon.class, method = "initializeRelicList")
public class RemoveRelic {
    @SpirePostfixPatch
    public static void Post() {
        if (characters.contains(AbstractDungeon.player.chosenClass)) {
            if (ModConfig.removeHpRelic){
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(Pear.ID));
                AbstractDungeon.shopRelicPool.removeIf(key -> key.equals(Waffle.ID));
                AbstractDungeon.commonRelicPool.removeIf(key -> key.equals(Strawberry.ID));
                AbstractDungeon.rareRelicPool.removeIf(key -> key.equals(Mango.ID));
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(SingingBowl.ID));
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(DarkstonePeriapt.ID));
            }
            if(ModConfig.removeHp2Relic)
            {
                AbstractDungeon.commonRelicPool.removeIf(key -> key.equals(ToyOrnithopter.ID));
                AbstractDungeon.commonRelicPool.removeIf(key -> key.equals(MealTicket.ID));
                AbstractDungeon.rareRelicPool.removeIf(key -> key.equals(BirdFacedUrn.ID));
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(EternalFeather.ID));
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(MeatOnTheBone.ID));
                AbstractDungeon.uncommonRelicPool.removeIf(key -> key.equals(Pantograph.ID));
                AbstractDungeon.commonRelicPool.removeIf(key -> key.equals(RegalPillow.ID));

            }

        }
    }
}
