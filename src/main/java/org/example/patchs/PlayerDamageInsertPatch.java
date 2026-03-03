package org.example.patchs;

// 导入必要的包
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import javassist.CtBehavior;
import org.example.Character.SlugCat;

import java.util.ArrayList;
// 替换为你的MOD实际包路径


/**
 * AbstractPlayer.damage()方法补丁：在if (currentHealth < 0) {}后插入SlugCat的behit()调用逻辑
 */
@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)
public class PlayerDamageInsertPatch {

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher strikeEffectMatcher = new Matcher.NewExprMatcher(StrikeEffect.class);
            int[] allMatches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), strikeEffectMatcher);


            int strikeEffectLine = allMatches[allMatches.length - 2];
            int targetLine = strikeEffectLine + 2;

            return new int[]{targetLine};
        }
    }


    @SpireInsertPatch(locator = Locator.class, localvars = {"info", "damageAmount"})
    public static void Insert(AbstractPlayer __instance, DamageInfo info,int damageAmount) {
        // 核心逻辑：调用SlugCat的behit()
        if(__instance.currentHealth<=0){
            if (__instance instanceof SlugCat) {
                ((SlugCat) __instance).behit();
            }
        }
        if (__instance instanceof SlugCat) {
            ((SlugCat) __instance).CheckSleep(damageAmount);
        }

    }
}