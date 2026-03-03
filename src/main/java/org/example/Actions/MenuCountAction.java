package org.example.Actions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.relics.RedCat_Menu;

public class MenuCountAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private AbstractCreature target;
    public MenuCountAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST) {
            if(target.isDead&&AbstractDungeon.player.hasRelic(RedCat_Menu.ID)){
                AbstractDungeon.player.getRelic(RedCat_Menu.ID).setCounter(1);
            }
            this.isDone = true;
        }
    }
}
