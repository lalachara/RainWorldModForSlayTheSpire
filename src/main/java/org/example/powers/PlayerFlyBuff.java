package org.example.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FlightPower;

public class PlayerFlyBuff extends FlightPower {
    public PlayerFlyBuff(AbstractCreature owner, int amount) {
        super(owner, amount);
    }

    @Override
    public void onRemove() {
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, POWER_ID)
        );
    }
}
