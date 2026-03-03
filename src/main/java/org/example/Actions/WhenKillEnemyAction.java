package org.example.Actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.relics.Liver_BoneSpear;

public class WhenKillEnemyAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int eventType;
    public AbstractCard c;

    public WhenKillEnemyAction(AbstractCreature target, DamageInfo info, int eventType) {
        this.info = info;
        setValues(target, info);
        this.eventType = eventType;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }


    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            if(info.output>=100&&eventType==2){
                AchievementMgr.unlockAchievement(9);
            }
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                    !this.target.hasPower("Minion")) {
                System.out.println("Enemy killed: " + this.target.name);
                doEvent();
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }

    public void GetFood() {
        if (AbstractDungeon.player instanceof SlugCat) {
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            liver.addFood(2);
        }
    }

    public void GetGold() {
        int goldAmount = 10;

        for (int i = 0; i < goldAmount; i++) {
            AbstractDungeon.effectList.add(
                    new GainPennyEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)
            );
        }
        addToBot(new com.megacrit.cardcrawl.actions.common.GainGoldAction(goldAmount));
    }

    public void UpgradeCard() {
//
//        if (c != null ) {
//            addToBot(new SpearShapeAction(c));
//        }
        if(info.output>0&&AbstractDungeon.player instanceof SlugCat && AbstractDungeon.player.hasRelic(Liver_BoneSpear.ID))
        {
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            liver.addFood(1);
        }
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.uuid == c.uuid) {
                card.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(card);
            }
        }
        c.upgrade();

    }
    public void ReturnTohand() {
        if (c != null) {
            c.cost = 0;
            c.setCostForTurn(-9);
            c.isCostModified = true;
            AbstractDungeon.player.hand.refreshHandLayout();
            addToBot(new AddToBotAction(new ReturnHandAction(c)));
        }
    }
    public void addWorkLevel(){
        if(AbstractDungeon.player instanceof SlugCat)
            ((SlugCat) AbstractDungeon.player).addWorkLevel(1);
    }

    public void doEvent() {
        switch (eventType) {
            case 1:
                GetFood();
                break;
            case 2:
                GetGold();
                break;
            case 3:
                UpgradeCard();
                break;
            case 4:
                addWorkLevel();
            break;
            case 5:
                ReturnTohand();
                break;


        }
    }
}
