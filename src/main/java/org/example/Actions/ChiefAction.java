package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.example.cards.Liver.attacks.Liver_Bomb;
import org.example.cards.Liver.attacks.Liver_SpearBoom;
import org.example.cards.Liver.attacks.Liver_SpearElec;
import org.example.cards.Liver.skills.Liver_Pearl;
import org.example.cards.Liver.skills.Liver_TreasureBag;
import org.example.cards.Liver.attacks.*;


public class ChiefAction extends AbstractGameAction {
    private String[] Cardlist = new String[]{
            Liver_SpearBoom.ID,
            Liver_Bomb.ID,
            Liver_SpearElec.ID,
            Liver_Pearl.ID,
            Liver_TreasureBag.ID
    };
    @Override
    public void update() {

        int random = AbstractDungeon.cardRandomRng.random(4);
        AbstractCard tempCard = CardLibrary.getCard(Cardlist[random]).makeCopy();
        if(tempCard.cost>0){
            tempCard.setCostForTurn(0);
            tempCard.cost = 0;
            tempCard.isCostModified = true;
        }

//        tempCard.isInnate = true;
        tempCard.exhaust = true;
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(tempCard));
        isDone = true;

    }
}
