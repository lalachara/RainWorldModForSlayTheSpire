package org.example.patchs;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.CoffeeDripper;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import org.example.Character.*;
import org.example.Events.CampMaxWLOption;
import org.example.Events.CampWLOption;
import org.example.relics.Liver_SleepPotions;

import java.util.ArrayList;

   @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
  public class CampPatch
  {
   @SpireInsertPatch(rloc = 33, localvars = {"buttons"})
    public static void Insert(CampfireUI _inst, ArrayList<AbstractCampfireOption> buttons) {

    if ((AbstractDungeon.player instanceof SlugCat)) {
        SlugCat p = (SlugCat) AbstractDungeon.player;
        if(!AbstractDungeon.player.hasRelic(CoffeeDripper.ID)){
            for (int i = 0; i < buttons.size(); i++) {
                if(buttons.get(i) instanceof RestOption)
                    buttons.set(i, new CampWLOption());
            }
        }
        if(p.maxWorkLevel<9&&!AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID)||AbstractDungeon.player.hasRelic(CoffeeDripper.ID))
            if((AbstractDungeon.id.equals("TheBeyond")&& !p.CampInBeyond)||
                    (AbstractDungeon.id.equals("TheCity")&& !p.CampInCity)||
                (AbstractDungeon.id.equals("Exordium")&& !p.CampInBottom))
                buttons.add(new CampMaxWLOption());

    }


    }
  }
