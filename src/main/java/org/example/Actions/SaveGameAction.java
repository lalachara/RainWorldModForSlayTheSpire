package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.example.Character.SlugCat;
import org.example.cards.Liver.attacks.Liver_Bomb;
import org.example.cards.Liver.attacks.Liver_SpearBoom;
import org.example.cards.Liver.attacks.Liver_SpearElec;
import org.example.cards.Liver.skills.Liver_Pearl;
import org.example.cards.Liver.skills.Liver_TreasureBag;


public class SaveGameAction extends AbstractGameAction {
    private String[] Cardlist = new String[]{
            Liver_SpearBoom.ID,
            Liver_Bomb.ID,
            Liver_SpearElec.ID,
            Liver_Pearl.ID,
            Liver_TreasureBag.ID
    };
    @Override
    public void update() {

       if(AbstractDungeon.player instanceof SlugCat)
           ((SlugCat)AbstractDungeon.player).SaveGame();

    }
}
