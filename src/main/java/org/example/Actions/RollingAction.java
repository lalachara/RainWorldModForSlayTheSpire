package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;



public class RollingAction extends AbstractGameAction {
    private boolean freeToPlayOnce;

    private boolean upgraded;

    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;
    private AbstractCard card;

    public RollingAction (AbstractPlayer p,AbstractMonster m, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, AbstractCard card) {
        this.p = p;
        this.m = m;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.card = card;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if(effect>0) {
            for (int i = 0; i < effect; i++) {
                addToBot( new DamageAction(m,new DamageInfo(p,card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                addToBot(new GainBlockAction(p, p, card.block));
            }
            //addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, card.block*effect),card.block*effect));

            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }


        this.isDone = true;
    }
}
