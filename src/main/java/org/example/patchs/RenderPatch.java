package org.example.patchs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.example.Character.*;
import org.example.Character.SlugCat;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "renderOrb"
)
public class RenderPatch {
    @SpirePostfixPatch
    public static void renderWorkBar(EnergyPanel __instance, SpriteBatch sb) {
        if (AbstractDungeon.player instanceof SlugCat &&AbstractDungeon.getCurrRoom() != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            //((SlugCat)AbstractDungeon.player).renderWorkLevelBar(sb);
            ((SlugCat)AbstractDungeon.player).renderUI(sb);
        }
    }
}