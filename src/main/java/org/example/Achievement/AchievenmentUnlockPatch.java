package org.example.Achievement;



import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.example.Character.Liver;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.powers.Inherent;
import org.example.tools.ModConfig;

import java.util.Set;

public class AchievenmentUnlockPatch {

    @SpirePatch(
            clz = StatsScreen.class,
            method = "incrementVictory"
    )
    public static class THE_ENDING {
        @SpirePostfixPatch
        public static void Postfix(CharStat c)
        {
            if(AbstractDungeon.player instanceof Liver){
                AchievementMgr.unlockAchievement(3);
            }else if(AbstractDungeon.player instanceof RedCat){
                AchievementMgr.unlockAchievement(4);
                if(!((RedCat) AbstractDungeon.player).HasGetCorrupt){
                    AchievementMgr.unlockAchievement(29);
                }
            }

            if(AbstractDungeon.player instanceof SlugCat){
                if(((SlugCat) AbstractDungeon.player).workLevel ==9){
                    AchievementMgr.unlockAchievement(5);
                    if(AbstractDungeon.player instanceof RedCat){
                        AchievementMgr.unlockAchievement(23);
                    }
                }
                if(!((SlugCat) AbstractDungeon.player).hasSleep){
                    AchievementMgr.unlockAchievement(26);
                }


            }


        }
    }


    @SpirePatch(clz = CorruptHeart.class, method = "die")
    public static class PatchDie {
        @SpireInsertPatch(rloc = 5)
        public static void Insert() {

            if(AbstractDungeon.player.hasPower(Inherent.POWER_ID)){
                if(((Inherent)AbstractDungeon.player.getPower(Inherent.POWER_ID)).turnCount==0)
                    AchievementMgr.unlockAchievement(15);
            }

            if(AbstractDungeon.ascensionLevel >=20&& ModConfig.realModeEnable)
            {
                if(AbstractDungeon.player instanceof RedCat)
                    AchievementMgr.unlockAchievement(31);
                if(AbstractDungeon.player instanceof Liver)
                    AchievementMgr.unlockAchievement(27);
            }

        }
    }
}
