package org.example.cards.Patch;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;


public abstract class MultiPreviewCard extends CustomCard {
    public ArrayList<String> previewCardIDs = new ArrayList<>();

    public MultiPreviewCard(String ID, String NAME, String IMG_PATH,
                            int COST, String DESCRIPTION, CardType TYPE,
                            CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }
    @Override
    public void updateHoverLogic() {
        super.updateHoverLogic();
        // 强制扩大悬停检测范围
        this.hb.update();
        this.hb.move(this.current_x, this.current_y + 50f);
    }

    public ArrayList<AbstractCard> getPreviewCards() {
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        for (String id : previewCardIDs) {
            AbstractCard c = CardLibrary.getCard(id).makeCopy();
            if(this.upgraded)
                c.upgrade();
            c.applyPowers();
            retVal.add(c);
        }
        return retVal;
    }
}
