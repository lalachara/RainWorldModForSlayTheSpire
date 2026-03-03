package org.example.cards.Patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;

import java.util.ArrayList;
import java.util.Iterator;

import static org.example.Character.SlugCat.Enums.CantRemoved_TAG;

@SpirePatch(clz = CardGroup.class, method = "getPurgeableCards")
public class CantRemovedCard {
    @SpirePostfixPatch
    public static CardGroup Replace(CardGroup __instance) {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : __instance.group) {
            // 原版排除条件
            boolean isExcludedCurse = c.cardID.equals("Necronomicurse") ||
                    c.cardID.equals("CurseOfTheBell") ||
                    c.cardID.equals("AscendersBane");

            // 额外排除自定义标签
            boolean isCantMove = c.hasTag(CantRemoved_TAG);

            if (!isExcludedCurse && !isCantMove) {
                retVal.addToTop(c);
            }
        }

        for (int j = 0; j < retVal.size(); j++) {
            System.out.println(retVal.group.get(j).name);
        }
        return retVal;
    }
}