package org.example.powers;

import basemod.devcommands.deck.DeckAdd;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Actions.AllCardInitDescription;
import org.example.Actions.GetSpearMeleeAction;
import org.example.Character.*;
import org.example.cards.RedCat.RedCatCard;
import org.example.cards.RedCat.RedCat_Fucycle;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;
import org.example.monsters.Cabrite;
import org.example.monsters.IronBird;
import org.example.monsters.MushRoom;
import org.example.relics.RedCat_Menu;
import org.example.relics.RedCat_Pearl;
import org.example.tools.ModConfig;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.TreasureSpear_TAG;
import static org.example.tools.Tools.GetRandomSpearAndAddTag;


public class Inherent extends AbstractPower  {
    public static final String POWER_ID = "Liver:Inherent";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_128 = "images/CharacterImg/Powers/Meow128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/Meow48.png";
    public boolean wawawa = false;
    public int usedSlimeCount = 0;
    private SlugCat player;
    private Hitbox hb ;
    public boolean getNimble = false;
    public boolean isClick = false;
    public int turnCount = 0,WorkLevelLossCount=0;
    public Inherent(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        hb = new Hitbox(36* Settings.scale, 36*Settings.scale);
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];
        usedSlimeCount = 0;

        Initregion();
        this.updateDescription();
        if(AbstractDungeon.player instanceof SlugCat)
            player = (SlugCat) AbstractDungeon.player;
    }
    private void Initregion() {
        if(owner instanceof RedCat){
            this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/CharacterImg/Powers/MeowRedCat128.png"), 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/CharacterImg/Powers/MeowRedCat48.png"), 0, 0, 48, 48);
        }
        else {
            this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
            this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);
        }

    }

    @Override
    public void onInitialApplication() {
        turnCount = 0;
        WorkLevelLossCount = 0;
        super.onInitialApplication();

    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
    }

    public void addWorkLevelLossCount(){
        WorkLevelLossCount++;
        if(WorkLevelLossCount==9){
           AchievementMgr.unlockAchievement(25);
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
    }

    @Override
    public void onGainedBlock(float blockAmount) {
//        if(owner.currentBlock>=999&&owner.hasPower(VigorPower.POWER_ID)&& TempHPField.tempHp.get(owner)>=999){
//            if(owner.getPower(VigorPower.POWER_ID).amount>=999)
//                AchievementMgr.unlockAchievement(36);
//        }
        super.onGainedBlock(blockAmount);
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);
        this.hb.update(x-18*Settings.scale,y-18*Settings.scale);
        renderhb(sb);
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if(owner.currentBlock>=999&&owner.hasPower(VigorPower.POWER_ID)&& TempHPField.tempHp.get(owner)>=999){
            if(owner.getPower(VigorPower.POWER_ID).amount>=999)
                AchievementMgr.unlockAchievement(13);
        }
//        if (this.hb.hovered && InputHelper.justClickedLeft) {
//            this.onClick();
//            isClick = true;
//        }else
//            isClick = false;
    }

    public void renderhb(SpriteBatch sb) {
        if (Settings.isDebug || Settings.isInfo) {
            sb.setColor(Color.RED);
            sb.draw(ImageMaster.DEBUG_HITBOX_IMG, hb.x, hb.y, hb.width, hb.height);
        }

    }

    @Override
    public void onVictory() {
        AchievementMgr.unlockAchievement(0);
        if(player instanceof Liver)
            ((Liver) player).kills++;

        
//        if(player instanceof SlugCat)
//            if(((SlugCat) player).workLevel==0)
//                AchievementMgr.unlockAchievement(24);

        if(AbstractDungeon.player.masterDeck.findCardById(RedCat_Fucycle.ID)!=null)
            addToBot(new AddCardToDeckAction(new RedCat_Fucycle()));


    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID.equals(NimbleBuff.POWER_ID)) {
            getNimble = true;
        }
        if(target.hasPower(LoveBloodBuff.POWER_ID)){
            target.getPower(LoveBloodBuff.POWER_ID).onApplyPower(power, target, source);
        }

        if(power.ID== VigorPower.POWER_ID&&AbstractDungeon.player.hasRelic(RedCat_Pearl.ID)){
            AbstractDungeon.player.getRelic(RedCat_Pearl.ID).setCounter(power.amount);
        }
//        if(owner.currentBlock>=999&&owner.hasPower(VigorPower.POWER_ID)&& TempHPField.tempHp.get(owner)>=999){
//            if(owner.getPower(VigorPower.POWER_ID).amount>=999)
//                AchievementMgr.unlockAchievement(36);
//        }

    }


//    @Override
//    public int onAttacked(DamageInfo info, int damageAmount) {
//        if(damageAmount>0&&owner instanceof RedCat)
//        {
//            RedCat p = (RedCat)owner;
//            p.addCorrupt(1);
//        }
//        return super.onAttacked(info, damageAmount);
//    }



    @Override
    public void atStartOfTurn() {

        turnCount++;


        getNimble = false;
//        if(player!=null)
//        {
//            if(player.uiController.sleepUI.picnum!=0)
//                player.uiController.sleepUI.picnum--;
//
//        }
//        if(owner instanceof RedCat){
//            RedCat p = (RedCat)owner;
//            p.addCorrupt(1);
//        }
        for (int i = 0; i < AbstractDungeon.player.hand.group.size(); i++) {
            if(AbstractDungeon.player.hand.group.get(i).hasTag(TreasureSpear_TAG))
                AbstractDungeon.player.hand.group.get(i).retain = true;
        }

        addToBot(new GetSpearMeleeAction());


    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        addToBot(new AllCardInitDescription());
        if(card.hasTag(TreasureSpear_TAG)){
            action.exhaustCard = true;
            AbstractCard c = GetRandomSpearAndAddTag(card.upgraded);
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
        }

    }




//    @Override
//    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
//        super.onAfterUseCard(card, action);
////        if(card.hasTag(Corrupt_Tag)){
////            if(AbstractDungeon.player instanceof RedCat)
////            {
////                ((RedCat) AbstractDungeon.player).addCorrupt(1);
////            }
////
//////            if(card instanceof RedCatCard)
//////            {
//////                //((RedCatCard) card).Corrupt();
//////            }
//////            else
//////                addToBot(new MakeTempCardInDiscardAction(new RedCat_Corrupt(),1));
////        }
//    }

    @Override
    public void onExhaust(AbstractCard card) {
        if(Objects.equals(card.cardID, Slimed.ID))
            usedSlimeCount++;
        if(AbstractDungeon.getMonsters().getMonster("Liver:MushRoom")!=null){
            for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
                if(AbstractDungeon.getMonsters().monsters.get(i).hasPower(ProliferationBuff.POWER_ID))
                {
                    ProliferationBuff buff = (ProliferationBuff) AbstractDungeon.getMonsters().monsters.get(i).getPower(ProliferationBuff.POWER_ID);
                    buff.onExhaust(card);
                }
            }
        }
//        if(card.hasTag(Corrupt_Tag)){
//            addToBot(new MakeTempCardInDiscardAction(new RedCat_Corrupt(),1));
//        }

    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @SpirePatch(clz = AbstractMonster.class, method = "die",paramtypez = {})
    public static class MonsterDeathCounter {
        @SpirePostfixPatch
        public static void countKill(AbstractMonster __instance) {

            if (__instance.isDying &&
                    !__instance.isEscaping &&
                    AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                    __instance.currentHealth <= 0 &&
                    !__instance.halfDead &&
                    !__instance.hasPower("Minion")&&
                    AbstractDungeon.player instanceof SlugCat) {
                SlugCat p = (SlugCat)AbstractDungeon.player;
                //p.kills++;
                if(p instanceof Liver)
                    p.addFood(1+__instance.maxHealth/30);
                if(p instanceof RedCat)
                    p.addFood(2+__instance.maxHealth/20);

            }

        }
    }
}
