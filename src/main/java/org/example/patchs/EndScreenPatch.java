package org.example.patchs;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import javassist.CtBehavior;
import org.example.Character.Liver;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;

@SpirePatch(clz = GameOverScreen.class, method = "renderStatsScreen")
public class EndScreenPatch {

    // 您的自定义纹理（在资源加载时初始化）
    public static Texture CUSTOM_BACKGROUND_Liver = new Texture("images/CharacterImg/RainWorld/EndScreen.png"); // 替换为您的自定义背景路径
    public static Texture CUSTOM_BACKGROUND_RedCat = new Texture("images/CharacterImg/RainWorld/EndScreenRedCat.png"); // 替换为您的自定义背景路径

    @SpireInsertPatch(rloc = 10) // 在方法开始后的第10行左右插入

    public static void Insert(
            GameOverScreen __instance,
            SpriteBatch sb) {
        if(AbstractDungeon.player instanceof SlugCat){
            Color originalColor = sb.getColor();
            // 设置自定义背景颜色和透明度
            Color bgColor = new Color(1, 1, 1, 1f);
            sb.setColor(bgColor);
            if(AbstractDungeon.player instanceof Liver)
            // 绘制自定义背景（全屏）
            sb.draw(
                    CUSTOM_BACKGROUND_Liver,
                    0,  // x 位置
                    0,  // y 位置
                    Settings.WIDTH,  // 宽度
                    Settings.HEIGHT  // 高度
            );
            else if(AbstractDungeon.player instanceof RedCat)
                sb.draw(
                        CUSTOM_BACKGROUND_RedCat,
                        0,  // x 位置
                        0,  // y 位置
                        Settings.WIDTH,  // 宽度
                        Settings.HEIGHT  // 高度
                );
            // 恢复原始颜色状态
            sb.setColor(originalColor);
        }
    }

    // 定位器 - 在原始背景绘制之后插入
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethod) throws Exception {
            // 找到绘制白色背景后的位置
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    StatsScreen.class,
                    "render"
            );
            return LineFinder.findInOrder(ctMethod, finalMatcher);
        }
    }
}