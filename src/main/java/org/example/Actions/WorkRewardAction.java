package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.attacks.RedCat_Avenger;
import org.example.cards.RedCat.attacks.RedCat_EatBlood;
import org.example.cards.RedCat.attacks.RedCat_Save;
import org.example.cards.RedCat.skills.RedCat_WillFlower;
import org.example.powers.RedCat.ExcitePower;
import org.example.powers.TwistPower;

import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT;
import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.WorkReward_Tag;

public class WorkRewardAction extends AbstractGameAction {


    public WorkRewardAction() {
        super();
    }

    public void update() {
        // 遍历抽牌堆
        for(AbstractCard c:AbstractDungeon.player.discardPile.group){
            checkCard(c);
        }
        for(AbstractCard c:AbstractDungeon.player.drawPile.group){
            checkCard(c);
        }
        // 遍历手牌
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            checkCard(c);
        }
        if(AbstractDungeon.player.hasPower(TwistPower.POWER_ID))
            addToBot(new DrawCardAction(AbstractDungeon.player.getPower(TwistPower.POWER_ID).amount));
        if(AbstractDungeon.player.hasPower(ExcitePower.POWER_ID)){
            if(AbstractDungeon.player instanceof SlugCat){
                ((SlugCat) AbstractDungeon.player).uiController.sleepUI.picnum=0;
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, AbstractDungeon.player.getPower(ExcitePower.POWER_ID).amount, DamageInfo.DamageType.THORNS, BLUNT_LIGHT));
            }
        }

        this.isDone = true;
    }
    private void checkCard(AbstractCard c){
        if(c instanceof RedCat_EatBlood)
            ((RedCat_EatBlood) c).addMisc();
        if(c instanceof RedCat_Save){
            c.modifyCostForCombat(-1);
        }
        if(c instanceof RedCat_WillFlower){
            c.misc+=1;
        }
        if(c instanceof RedCat_Avenger){
            c.misc+=1;
        }
    }
}