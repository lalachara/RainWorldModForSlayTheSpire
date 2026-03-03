package org.example.Events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.Character.SlugCat;
import org.example.cards.Liver.attacks.Liver_ThreeAgree;
import org.example.cards.Liver.powers.Liver_WorkError;
import org.example.cards.Liver.skills.Liver_TwistFate;
import org.example.cards.RedCat.RedCat_Fucycle;
import org.example.relics.Liver_Eternal;
import org.example.relics.Liver_MoonSkin;
import org.example.relics.Liver_TalkSign;
import org.example.relics.RedCat_mushroom;

public class Fucycle extends AbstractImageEvent {
    public static final String ID = "RedCat:Fucycle";
    private static final String IMG = "images/CharacterImg/Events/fucycle.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    boolean hasSelect = false;
    boolean back = false;
    boolean fight = false;
    boolean rewards = false;
    int lastchoose;

    // 添加状态跟踪
    private enum EventState {
        INITIAL,
        AFTER_FIRST_CHOICE,
        AFTER_BACK,
        POST_COMBAT,
        LEAVE
    }
    private EventState state = EventState.INITIAL;

    public Fucycle() {
        super(NAME, DESCRIPTIONS[0], IMG);
        if(AbstractDungeon.player instanceof SlugCat){
            if(((SlugCat)AbstractDungeon.player).workLevel==9)
                this.imageEventText.setDialogOption(OPTIONS[0], new RedCat_Fucycle());
            else
                this.imageEventText.setDialogOption(OPTIONS[4],true);
            if(((SlugCat)AbstractDungeon.player).workLevel>=4)
                this.imageEventText.setDialogOption(OPTIONS[1]);
            else
                this.imageEventText.setDialogOption(OPTIONS[4],true);
        }else{
            this.imageEventText.setDialogOption(OPTIONS[4],true);
            this.imageEventText.setDialogOption(OPTIONS[4],true);
        }


        this.imageEventText.setDialogOption(OPTIONS[2],new RedCat_mushroom());
        this.imageEventText.setDialogOption(OPTIONS[3], new RedCat_Fucycle());

    }


    // 添加 reopen 方法处理战斗后返回
    @Override
    public void reopen() {
        if (fight) {
            // 战斗结束后返回事件
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = Settings.WIDTH * 0.25F;
            AbstractDungeon.player.preBattlePrep();
            enterImageFromCombat();

            // 更新界面为战斗后状态
            state = EventState.POST_COMBAT;
            this.imageEventText.loadImage("images/CharacterImg/Events/GoldSea.png");
            this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // 添加新的描述文本
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[6],new Liver_Eternal()); // "继续探索"
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (state) {
            case INITIAL:
                handleInitialChoice(i);
                break;

            case POST_COMBAT:
                handlePostCombat(i);
                break;

            case LEAVE:
                openMap();
                break;
        }
    }

    private void handleInitialChoice(int i) {

        switch (i){
            case 0://fight
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new RedCat_Fucycle(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));

                AbstractDungeon.getCurrRoom().rewardAllowed = false;
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Rainworld:Fires");
                AbstractDungeon.getCurrRoom().rewards.clear();
                enterCombatFromImage();
                fight = true;



                break;
            case 1:
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // 添加新的描述文本
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[5]); // "继续探索"
                if(AbstractDungeon.player instanceof SlugCat){
                    ((SlugCat)AbstractDungeon.player).addMaxWorkLevel(-1);
                }
                state = EventState.LEAVE;
                break;
            case 2:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new RedCat_mushroom());
                this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // 添加新的描述文本
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[5]); // "继续探索"
                state = EventState.LEAVE;
                break;
            case 3:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new RedCat_Fucycle(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[4]); // 添加新的描述文本
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[5]); // "继续探索"
                state = EventState.LEAVE;
                break;
        }
    }


    private void handlePostCombat(int i) {
        // 战斗结束后点击继续
        if(!rewards){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Liver_Eternal());
            this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // 添加新的描述文本
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[5]); // "继续探索"
            rewards = true;
            state = EventState.LEAVE;
        }else {
            openMap();
        }


    }
}
