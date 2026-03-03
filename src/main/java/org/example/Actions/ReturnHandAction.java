package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.powers.RedCat.StudyPower;

public class ReturnHandAction extends AbstractGameAction {

    public AbstractCard c;

    public ReturnHandAction(AbstractCard c) {
        this.c = c;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }


    @Override
    public void update() {
        ReturnTohand();
        this.isDone = true;
        tickDuration();
    }


    public void ReturnTohand() {
        if (c != null&& AbstractDungeon.player.hand.size()<10) {
            AbstractDungeon.player.hand.refreshHandLayout();
            addToBot(new DiscardToHandAction(c) );
            if(AbstractDungeon.player.hasPower(StudyPower.POWER_ID)){
                c.damage+=AbstractDungeon.player.getPower(StudyPower.POWER_ID).amount;
                c.baseDamage+=AbstractDungeon.player.getPower(StudyPower.POWER_ID).amount;
                c.retain = true;
            }
        }
    }


}
