package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.attacks.RedCat_CorruptStrike;
import org.example.cards.RedCat.skills.RedCat_CorruptBlock;

import java.util.ArrayList;
import java.util.Iterator;


public class RedCat_CorruptCatalyst extends CustomRelic {
    public static final String ID = "RedCat:CorruptCatalyst";
    private static final String IMG_PATH = "images/CharacterImg/Relics/redmushroom.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/redmushroom.png";
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;



    public RedCat_CorruptCatalyst() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        ArrayList<AbstractCard> tempcards = new ArrayList<>();
            int i;
           for (i = masterDeck.size() - 1; i >= 0; i--) {
                AbstractCard card = masterDeck.get(i);
               if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                      AbstractDungeon.player.masterDeck.removeCard(card);
                      AbstractCard corruptStrike = new RedCat_CorruptStrike();
                      if(card.upgraded){
                          corruptStrike.upgrade();
                      }
                      tempcards.add(corruptStrike);
                  }else if(card.tags.contains(AbstractCard.CardTags.STARTER_DEFEND)){
                   AbstractDungeon.player.masterDeck.removeCard(card);
                   AbstractCard corruptBlock = new RedCat_CorruptBlock();
                     if(card.upgraded){
                          corruptBlock.upgrade();
                     }
                        tempcards.add(corruptBlock);
               }



          }
        for (i = 0; i < tempcards.size(); i++) {
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard)tempcards.get(i), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
        super.onEquip();
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof RedCat;
    }

}