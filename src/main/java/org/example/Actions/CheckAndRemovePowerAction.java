package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class CheckAndRemovePowerAction extends AbstractGameAction {
    private AbstractPower power;

    public CheckAndRemovePowerAction(AbstractPower power) {
        this.power = power;
    }

    public void update() {
            if(power.amount<=0){
                addToBot(new RemoveSpecificPowerAction(power.owner,power.owner,power.ID));
            }
            this.isDone = true;

    }
}
