package org.example.patchs;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import org.example.Character.*;
import org.example.cards.Liver.others.Liver_stubborn;
import org.example.tools.Tools;

@SpirePatch(
        clz = TopPanel.class,
        method = "updateTips"
)
public class TopPanelTipsPatch {
    private static final UIStrings Strings = CardCrawlGame.languagePack.getUIString("Liver:HpPatch");
    @SpireInsertPatch(rloc = 2)
    public static void beforeRender(TopPanel __instance) {
        if(AbstractDungeon.player instanceof SlugCat){
            SlugCat liver = (SlugCat)AbstractDungeon.player;
            TipHelper.renderGenericTip((float) InputHelper.mX - 140.0F * Settings.scale,(float)Settings.HEIGHT - 120.0F * Settings.scale, Strings.TEXT[0], Strings.TEXT[1]+(liver.workLevel+1)+"/"+(Tools.hasCard(Liver_stubborn.ID)?liver.maxWorkLevel:(liver.maxWorkLevel+1)));

        }
    }
}