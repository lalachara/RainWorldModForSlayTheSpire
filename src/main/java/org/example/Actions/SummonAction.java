package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.example.tools.Tools;

import static org.example.Character.SlugCat.Enums.CantCorrupt_Tag;
import static org.example.Character.SlugCat.Enums.Corrupt_Tag;


public class SummonAction extends AbstractGameAction {
        private boolean isCorrupt;
        public SummonAction(boolean isCorrupt) {
        this.isCorrupt = isCorrupt;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    public void update() {
            if(isCorrupt) {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (!c.hasTag(Corrupt_Tag) && !c.hasTag(CantCorrupt_Tag)) {
                        Tools.CorruptCard(c);
                    }
                }
            }
//            }else {
//                for (AbstractCard c : DrawCardAction.drawnCards){
//                    if(c.hasTag(Corrupt_Tag)){
//                        Tools.DeCorruptCard(c);
//                    }
//                }
//            }
          this.isDone = true;
        }
}
