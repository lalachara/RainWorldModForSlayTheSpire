/*    */
package org.example.patchs;
/*    */
/*    */

import basemod.helpers.SuperclassFinder;
/*    */ import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.spine.Skeleton;
/*    */ import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
/*    */ import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
/*    */ import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.rooms.ShopRoom;
/*    */ import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;
import org.example.tools.ModConfig;
/*    */ import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.evacipated.cardcrawl.modthespire.lib.SpirePatch.CONSTRUCTOR;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static com.megacrit.cardcrawl.shop.Merchant.DRAW_X;
import static com.megacrit.cardcrawl.shop.Merchant.DRAW_Y;
import static org.example.Achievement.SelectScreenPatch.characters;


/*    */ public class MerchantPatch
        /*    */ {/*    */

    @SpirePatch(clz = Merchant.class, method = CONSTRUCTOR, paramtypez = {})
    public static class MerchantConstructorPatch {

        /*    */
        @SpirePostfixPatch
        /*    */ public static void postfix(Merchant __instance) throws Exception {
            /*    */
            try {
                if (characters.contains(AbstractDungeon.player.chosenClass) && ModConfig.enableShopSkin)
                {
                    __instance.anim = new AnimatedNpc(DRAW_X + 256.0F * Settings.scale, AbstractDungeon.floorY + 30.0F * Settings.scale, "images/npc/merchant/skeleton.atlas", "images/npc/merchant/skeleton.json", "idle");
                    ImageMaster.MERCHANT_RUG_IMG = loadImage("images/npc/merchantObjects.png");
                }else
                    ImageMaster.MERCHANT_RUG_IMG = loadImage("images/npcs/merchantObjects.png");

            }
            /* 34 */ catch (Exception exception) {
            }

        }

    }
//    @SpirePatch(clz = Merchant.class, method = "render" )
//    public static class MerchantRugPatch {
//        public static void replace(Merchant __instance, SpriteBatch sb) {
//            sb.setColor(Color.WHITE);
//            if (characters.contains(AbstractDungeon.player.chosenClass) && ModConfig.enableShopSkin){
//                sb.draw(rug, DRAW_X +  ((__instance.hb.cX-DRAW_X)/Settings.scale-250F), DRAW_Y + ((__instance.hb.cY-DRAW_Y)/Settings.scale-130F), 512.0F * Settings.scale, 512.0F * Settings.scale);
//                System.out.println("执行替换操作");
//            }
//            else{
//                sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X +  ((__instance.hb.cX-DRAW_X)/Settings.scale-250F), DRAW_Y + ((__instance.hb.cY-DRAW_Y)/Settings.scale-130F), 512.0F * Settings.scale, 512.0F * Settings.scale);
//                System.out.println("未执行替换操作");
//            }
//            if (__instance.hb.hovered) {
//                sb.setBlendFunction(770, 1);
//                sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
//                if (characters.contains(AbstractDungeon.player.chosenClass) && ModConfig.enableShopSkin)
//                    sb.draw(rug, DRAW_X +  ((__instance.hb.cX-DRAW_X)/Settings.scale-250F), DRAW_Y + ((__instance.hb.cY-DRAW_Y)/Settings.scale-130F), 512.0F * Settings.scale, 512.0F * Settings.scale);
//                else
//                    sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X +  ((__instance.hb.cX-DRAW_X)/Settings.scale-250F), DRAW_Y + ((__instance.hb.cY-DRAW_Y)/Settings.scale-130F), 512.0F * Settings.scale, 512.0F * Settings.scale);
//                sb.setBlendFunction(770, 771);
//            }
//
//            if (__instance.anim != null) {
//                __instance.anim.render(sb);
//            }
//
//            if (Settings.isControllerMode) {
//                sb.setColor(Color.WHITE);
//                sb.draw(CInputActionSet.select.getKeyImg(), DRAW_X - 32.0F + 150.0F * Settings.scale, DRAW_Y - 32.0F + 100.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
//            }
//
//            __instance.hb.render(sb);
//        }
//    }



    @SpirePatch(clz = ShopScreen.class, method = "init")

    /*    */ public static class MerchantHandPatch {
        /*    */
        @SpirePostfixPatch
        /*    */ public static void postfix(ShopScreen __instance) throws Exception {
            /*    */
            try {
                if (characters.contains(AbstractDungeon.player.chosenClass) && ModConfig.enableShopSkin) {

                    Field handImgField = ShopScreen.class.getDeclaredField("handImg");
                    handImgField.setAccessible(true);
                    Texture customHandImg = loadImage("images/npc/merchantHand.png");
                    handImgField.set(__instance, customHandImg);

                }

            }
            /* 34 */ catch (Exception exception) {
            }
            /*    */
        }

        /*    */
    }
    /*    */
}


