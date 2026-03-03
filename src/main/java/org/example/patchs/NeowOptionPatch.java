package org.example.patchs;

// 文件：org/example/tools/NeowRewardPatch.java


import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;
import javassist.CtBehavior;
import org.example.Character.*;

@SpirePatch(
        clz = NeowEvent.class,
        method = "blessing"
)
public class NeowOptionPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(NeowEvent __instance) {
        if(AbstractDungeon.player instanceof SlugCat) {
            try {
                java.lang.reflect.Field rewardsField = NeowEvent.class.getDeclaredField("rewards");
                rewardsField.setAccessible(true);
                java.util.List rewards = (java.util.List) rewardsField.get(__instance);
                rewards.clear();
                rewards.add(new MyNeowReward(0));
                rewards.add(new MyNeowReward(1));
                rewards.add(new MyNeowReward(2));
                rewards.add(new MyNeowReward(3));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // 静态内部类，继承
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // 用Matcher定位插入点，这里以调用 clearRemainingOptions 为例
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.events.RoomEventDialog", "clearRemainingOptions"
            );
            // 返回插入点行号
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}