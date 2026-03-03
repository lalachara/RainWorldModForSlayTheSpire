package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.example.cards.Liver.attacks.Liver_Bomb;
import org.example.cards.Liver.attacks.Liver_SpearBoom;
import org.example.cards.Liver.attacks.Liver_SpearElec;
import org.example.cards.Liver.skills.Liver_Pearl;
import org.example.cards.Liver.skills.Liver_TreasureBag;
import org.example.powers.FreeVigorPower;


public class QucikRunAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;
    private AbstractCard card;

    public QucikRunAction (AbstractPlayer p, AbstractMonster m, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, AbstractCard card) {
        this.p = p;
        this.m = m;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.card = card;
    }
    @Override
    public void update() {

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if(effect!=0){
            addToBot(new ApplyPowerAction(p,p,new VigorPower(p,effect*card.magicNumber), effect*card.magicNumber));
            addToBot(new ApplyPowerAction(p,p,new FreeVigorPower(p, effect), effect));
        }


        if (!this.freeToPlayOnce)
            this.p.energy.use(EnergyPanel.totalCount);

        isDone = true;

    }
}
