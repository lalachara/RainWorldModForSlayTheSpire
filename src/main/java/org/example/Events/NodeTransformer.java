package org.example.Events;

import com.badlogic.gdx.utils.Timer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import com.megacrit.cardcrawl.vfx.scene.CampfireSmokeEffect;
import com.megacrit.cardcrawl.vfx.scene.EventBgParticle;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class NodeTransformer {

    // 将当前事件节点转变为商店
    public static void transformToShop() {
        // 创建商店房间
        ShopRoom shopRoom = new ShopRoom();
        // 获取当前地图节点
//        MapRoomNode currNode = AbstractDungeon.getCurrMapNode();
//
//        // 更新节点类型
//        currNode.room = shopRoom;
//        currNode.setRoom(shopRoom);

        // 设置当前房间
        //AbstractDungeon.currMapNode = currNode;
        //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        prepareRoomTransition(shopRoom);
    }

    // 将当前事件节点转变为篝火
    public static void transformToRest() {
        // 创建篝火房间
        RestRoom restRoom = new RestRoom();

        // 获取当前地图节点
//        MapRoomNode currNode = AbstractDungeon.getCurrMapNode();
//
//        // 更新节点类型
//        currNode.room = restRoom;
//        currNode.setRoom(restRoom);

        // 设置当前房间
        //AbstractDungeon.currMapNode = currNode;
        //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        prepareRoomTransition(restRoom);
    }
    private static void prepareRoomTransition(AbstractRoom newRoom) {
        // 1. 触发淡出效果
        boolean fastmode = Settings.FAST_MODE;
        Settings.FAST_MODE = false;
        //nextRoomTransitionStart();        // 2. 延迟执行房间切换（等待淡出完成）
        //AbstractDungeon.fadeOut();

        overlayMenu.proceedButton.hide();
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
                MapRoomNode node = AbstractDungeon.getCurrMapNode();
                node.room = newRoom;
                AbstractDungeon.nextRoom = node;

                // 3. 初始化新房间（但不标记为完成）
                newRoom.onPlayerEntry();
                // 4. 开始房间过渡流程

                nextRoomTransitionStart();

                // 5. 添加渐变粒子（此时更有效）
                AbstractDungeon.topLevelEffects.add(new FadeWipeParticle());
                //AbstractDungeon.fadeIn();
                Settings.FAST_MODE = fastmode;
//            }
//        }, 0.1F); // 匹配淡出时长（默认0.8秒）
    }
}