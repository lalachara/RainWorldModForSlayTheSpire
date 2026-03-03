package org.example.cards.Patch;

// 导入必要的包
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.example.powers.RedCat.RedMushroomBuff;

import java.util.ArrayList;

import static org.example.Character.SlugCat.Enums.Immunity_Tag;
// 替换为你的MOD实际包路径

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "draw",
        paramtypez = {int.class}
)
public class ImmunityPatch {

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // 1. 精准匹配：AbstractDungeon.effectList.add(new StrikeEffect(...)) 这一行
            Matcher strikeEffectMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "removeTopCard");
            int[] allMatches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), strikeEffectMatcher);

            // 2. 取damageAmount>0块内的那个StrikeEffect（对应你代码里的第129行左右）
            int strikeEffectLine = allMatches[0]; // 精准偏移：指向StrikeEffect行
            int targetLine = strikeEffectLine + 1; // +1 就是if (currentHealth < 0) 这一行的下一行

            return new int[]{targetLine};
        }
    }


    @SpireInsertPatch(locator = Locator.class, localvars = {"c"})
    public static void Insert(AbstractPlayer __instance,int nubnumCards, AbstractCard c) {
        if(c.hasTag(Immunity_Tag)){
            if(!__instance.hasPower(RedMushroomBuff.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
            }
        }

    }
}