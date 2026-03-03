//package org.example.patchs;
//
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
//import com.megacrit.cardcrawl.core.Settings;
//import org.example.Character.*;
//
//import java.lang.reflect.Field;
//@SpirePatch(
//        clz = EndTurnButton.class,
//        method = SpirePatch.CONSTRUCTOR
//)
//public class MoveEndTurnButton {
//    @SpirePostfixPatch
//    public static void afterInit(EndTurnButton __instance) {
//        if(AbstractDungeon.player instanceof SlugCat)
//            try {
//                Field yField = EndTurnButton.class.getDeclaredField("current_y");
//                yField.setAccessible(true);
//                float oldY = yField.getFloat(__instance);
//                yField.setFloat(__instance, oldY + 60F* Settings.scale);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        //MoveEndTurnButton.moveButtonUp(60.0F * Settings.scale);
//    }
//
//
//}