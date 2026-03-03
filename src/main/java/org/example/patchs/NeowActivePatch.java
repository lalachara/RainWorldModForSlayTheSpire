package org.example.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import org.example.Character.*;

import static org.example.Character.SlugCat.Enums.AddMaxWorkLevel;
import static org.example.Character.SlugCat.Enums.AddWorkLevel;

@SpirePatch(
        clz = NeowReward.class,
        method = "activate"
)
public class NeowActivePatch {
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Liver");

    @SpirePrefixPatch
    public static void Prefix1(NeowReward __instance) {

        if (__instance.type == AddWorkLevel) {
            // 这里写你要的逻辑
            if(AbstractDungeon.player instanceof SlugCat) {
                SlugCat liver = (SlugCat) AbstractDungeon.player;
                liver.addWorkLevel(1);
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(liver.hb.cX - liver.animX, liver.hb.cY, characterStrings.TEXT[3] + "1", Settings.GREEN_TEXT_COLOR));

            }
        }
    }
    @SpirePrefixPatch
    public static void Prefix2(NeowReward __instance) {
        if (__instance.type == AddMaxWorkLevel) {
            // 这里写你要的逻辑
            if(AbstractDungeon.player instanceof SlugCat) {
                SlugCat liver = (SlugCat) AbstractDungeon.player;
                liver.addMaxWorkLevel(1);
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(liver.hb.cX - liver.animX, liver.hb.cY, characterStrings.TEXT[4] + "1", Settings.GREEN_TEXT_COLOR));

            }
        }
    }
}