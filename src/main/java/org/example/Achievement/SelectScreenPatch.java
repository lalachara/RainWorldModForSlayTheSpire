package org.example.Achievement;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import org.example.Character.SlugCat;
import org.example.UIs.AchievementUI;

import java.util.ArrayList;
import java.util.List;

import static org.example.Character.SlugCat.PlayerColorEnum.Liver_CLASS;
import static org.example.Character.SlugCat.PlayerColorEnum.RedCat_CLASS;

public class SelectScreenPatch {

    private static final float startX = 1600.0F * Settings.scale;
    private static final float startY = Settings.HEIGHT - 100.0F * Settings.scale;
    private static final float padX = 100.0F * Settings.scale;
    private static final float padY = 100.0F * Settings.scale;
    public static List<AbstractPlayer.PlayerClass> characters = new ArrayList<>();

    static {
        characters.add(RedCat_CLASS);
        characters.add(Liver_CLASS);
    }

    public static boolean isPuzzlerSelected() {
        return ((characters.contains(CardCrawlGame.chosenCharacter)) && ((Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected")).booleanValue());
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    /*    */ public static class RenderButtonPatch
            /*    */ {
        /*    */
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            /* 29 */
            if (SelectScreenPatch.isPuzzlerSelected()) {
                /* 30 */
                AchievementUI.instance.render(sb);

                /*    */
            }
            /*    */
        }
        /*    */
    }

    /*    */
    /*    */
    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
     public static class UpdateButtonPatch {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (SelectScreenPatch.isPuzzlerSelected()) {
                AchievementUI.instance.update();
            } else {
                if(AchievementUI.shown){
                    AchievementUI.toggle();
                }


            }

            /*    */
        }
        /*    */
    }

}
