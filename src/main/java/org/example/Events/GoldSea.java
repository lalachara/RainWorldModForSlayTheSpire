package org.example.Events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.example.cards.Liver.attacks.Liver_ThreeAgree;

import org.example.cards.Liver.powers.Liver_WorkError;
import org.example.cards.Liver.skills.Liver_TwistFate;
import org.example.relics.Liver_Eternal;
import org.example.relics.Liver_MoonSkin;
import org.example.relics.Liver_TalkSign;

public class GoldSea extends AbstractImageEvent {
    public static final String ID = "Liver:GoldSea";
    private static final String IMG = "images/CharacterImg/Events/GoldSea.png";
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
        POST_COMBAT
    }
    private EventState state = EventState.INITIAL;

    public GoldSea() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.imageEventText.setDialogOption(OPTIONS[0], new Liver_TwistFate());
        this.imageEventText.setDialogOption(OPTIONS[1], new Liver_WorkError());
        this.imageEventText.setDialogOption(OPTIONS[2], new Liver_ThreeAgree());
        this.imageEventText.setDialogOption(OPTIONS[4]);
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
            this.imageEventText.updateBodyText(DESCRIPTIONS[8]); // 添加新的描述文本
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[7],new Liver_Eternal()); // "继续探索"
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (state) {
            case INITIAL:
                handleInitialChoice(i);
                break;

            case AFTER_FIRST_CHOICE:
                handleAfterFirstChoice(i);
                break;

            case AFTER_BACK:
                handleAfterBack(i);
                break;

            case POST_COMBAT:
                handlePostCombat(i);
                break;
        }
    }

    private void handleInitialChoice(int i) {
        this.hasSelect = true;
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[3]);
        lastchoose = i;

        switch (i) {
            case 0:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_TwistFate(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[1] + DESCRIPTIONS[4]);
                this.imageEventText.loadImage("images/CharacterImg/Events/GoldSea1.png");
                break;
            case 1:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_WorkError(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[2] + DESCRIPTIONS[4]);
                this.imageEventText.loadImage("images/CharacterImg/Events/GoldSea2.png");
                break;
            case 2:
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_ThreeAgree(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[3] + DESCRIPTIONS[4]);
                this.imageEventText.loadImage("images/CharacterImg/Events/GoldSea3.png");
                break;
            case 3:
                this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                break;
        }

        state = EventState.AFTER_FIRST_CHOICE;
    }

    private void handleAfterFirstChoice(int i) {
        openMap();
        this.imageEventText.clearAllDialogs();

        if (lastchoose != 3) {
            this.imageEventText.updateBodyText(AbstractDungeon.player.hasRelic(Liver_TalkSign.ID) ?
                    DESCRIPTIONS[7] : DESCRIPTIONS[6]);

            if (AbstractDungeon.player.hasRelic(Liver_MoonSkin.ID) &&
                    AbstractDungeon.player.hasRelic(Liver_TalkSign.ID)) {
                this.imageEventText.setDialogOption(OPTIONS[5]);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[6], true);
            }

            this.imageEventText.setDialogOption(OPTIONS[4]);
            back = true;
            state = EventState.AFTER_BACK;
        } else {
            // 如果选择3直接结束事件
            state = EventState.POST_COMBAT;
        }
    }

    private void handleAfterBack(int i) {
        if (i == 0) {
            AbstractDungeon.getCurrRoom().rewardAllowed = false;
            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Liver:Hide");
            AbstractDungeon.getCurrRoom().rewards.clear();
            enterCombatFromImage();
            fight = true;
        } else if (i == 1) {
            openMap();
        }
    }

    private void handlePostCombat(int i) {
        // 战斗结束后点击继续
        if(!rewards){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new Liver_Eternal());
            this.imageEventText.updateBodyText(DESCRIPTIONS[8]); // 添加新的描述文本
            this.imageEventText.clearAllDialogs();
            this.imageEventText.setDialogOption(OPTIONS[3]); // "继续探索"
            rewards = true;
        }else {
            openMap();
        }


    }
}
