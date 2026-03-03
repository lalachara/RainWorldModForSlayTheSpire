package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.Character.*;
import org.example.powers.HuntSignBuff;
import org.example.powers.SpearMasterNoteBuff;
import org.example.relics.Liver_BoneSpear;

public class SpearDamageAction extends DamageAction {
    private DamageInfo info;
    boolean addfooddown = false;
    public SpearDamageAction(AbstractCreature target, DamageInfo info) {
        super(target, info, AttackEffect.SLASH_DIAGONAL);
        this.info = info;
    }

    @Override
    public void update() {
        super.update();
        if(!addfooddown&&info.output>target.currentBlock&&AbstractDungeon.player instanceof SlugCat && AbstractDungeon.player.hasRelic(Liver_BoneSpear.ID))
        {
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            liver.addFood(1);
            addfooddown = true;
        }
    }
}
