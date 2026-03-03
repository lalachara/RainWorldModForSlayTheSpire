package org.example.Events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;
import org.example.cards.Liver.others.Liver_HeartFear;
import org.example.cards.Liver.skills.Liver_Neurone;
import org.example.patchs.BattleStartEvent;
import org.example.relics.Liver_Eternal;
import org.example.relics.Liver_MoonSkin;
import org.example.relics.Liver_TalkSign;
import org.example.tools.Tools;




public class Watcher extends AbstractImageEvent {
    public static final String ID = "Rainworld:Watcher";
    private static final String IMG = "images/CharacterImg/Events/Watcher1.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;


    private enum EventState {
        FIRST_CHOOSE,
        EAT_AGAIN,
        LEAVE,
        FIND,
        FIGHT,
        END
    }
    private EventState state = EventState.FIRST_CHOOSE ;

//    boolean hasSelect = false;
//    boolean leave = false;
//    boolean find = false;
//    boolean fight =false;
//    boolean isFight = false;
    boolean endsign = false;
    int eattimes = 0;
    boolean firstreopen =true;
    boolean rereopen = false;
    public Watcher() {

        super(NAME,DESCRIPTIONS[0], IMG);


        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {
        System.out.println("执行选项："+i);
        switch (state) {
            case FIRST_CHOOSE:
                firstchoose(i);
                return;
            case LEAVE:
                openMap();
                return;
            case EAT_AGAIN:
                eatagain(i);
                return;
            case FIND:
                find();
                return;
            case FIGHT:
                fight();
                return;
            default:
                openMap();



        }openMap();
    }
    private void firstchoose(int i){
        this.imageEventText.clearAllDialogs();
        if(i==0){
            this.state = EventState.EAT_AGAIN;
            this.imageEventText.setDialogOption(OPTIONS[2]);
            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            this.imageEventText.loadImage(IMG);
            if(AbstractDungeon.player instanceof SlugCat){
                SlugCat player = (SlugCat)AbstractDungeon.player;
                player.forceIncreaseMaxHp(1,true);
            }
            eattimes++;
        }

        else{
            this.state = EventState.LEAVE;
            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
            this.imageEventText.loadImage(IMG);
        }
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }
    private void eatagain(int i){
        this.imageEventText.clearAllDialogs();
        if(i==0)
            this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
        switch (i) {
            case 0:
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.loadImage(IMG);
                if(AbstractDungeon.player instanceof SlugCat){
                    SlugCat player = (SlugCat)AbstractDungeon.player;
                    player.forceIncreaseMaxHp(1,true);
                }
                if(0==AbstractDungeon.eventRng.random(4))
                    this.state = EventState.FIND;
                eattimes++;
                break;
            case 1:
                this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                this.imageEventText.loadImage(IMG);
                this.state = EventState.LEAVE;
                break;
        }
    }
    private void find(){
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONS[3],true);

        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
        this.imageEventText.loadImage("images/CharacterImg/Events/Watcher2.png");
        this.state = EventState.FIGHT;
    }
    private void fight(){
        System.out.println("再次执行fight");

                this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // 添加新的描述文本
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Rainworld:Watcher");
                if(AbstractDungeon.player instanceof SlugCat){
                    ((SlugCat)AbstractDungeon.player).eattimes=eattimes;
                }
                AbstractDungeon.getCurrRoom().rewards.clear();
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
                AbstractDungeon.getCurrRoom().addGoldToRewards(100);
                AbstractDungeon.getCurrRoom().eliteTrigger = true;
                this.enterCombatFromImage();
                AbstractDungeon.lastCombatMetricKey = "Rainworld:Watcher";
                this.imageEventText.clearRemainingOptions();


    }

//    @Override
//    public void reopen() {
//        System.out.println("why reopen?");
//        if(firstreopen)
//            firstreopen = false;
//        else if(!rereopen){
//            rereopen = true;
//            openMap();
//        }
//
//        //openMap();
//    }
    //    @Override
//    protected void buttonEffect(int i) {
//            if (!this.hasSelect) {
//                 this.hasSelect = true;
//                 this.imageEventText.clearAllDialogs();
//                 if(i==0)
//                    this.imageEventText.setDialogOption(OPTIONS[2]);
//                 this.imageEventText.setDialogOption(OPTIONS[1]);
//                 switch (i) {
//                   case 0:
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                       this.imageEventText.loadImage(IMG);
//                       if(AbstractDungeon.player instanceof SlugCat){
//                           SlugCat player = (SlugCat)AbstractDungeon.player;
//                           player.forceIncreaseMaxHp(1,true);
//                       }
//                       eattimes++;
//                       break;
//                   case 1:
//                       this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
//                       this.imageEventText.loadImage(IMG);
//                        leave=true;
//                        break;
//                 }
//
//          } else {
//                if(leave)
//                    openMap();
//                else if(fight)
//                {
//                    if(isFight) {
//                        this.imageEventText.clearAllDialogs();
//                        this.imageEventText.setDialogOption(OPTIONS[1]);
//                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
//                        this.imageEventText.loadImage("images/CharacterImg/Events/Watcher1.png");
//                        leave = true;
//
//                    }
//                    else {
//                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Rainworld:Watcher");
//                        if(AbstractDungeon.player instanceof SlugCat){
//                            ((SlugCat)AbstractDungeon.player).eattimes=eattimes;
//                        }
//                        AbstractDungeon.getCurrRoom().rewards.clear();
//                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
//                        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
//                        AbstractDungeon.getCurrRoom().eliteTrigger = true;
//                        enterCombatFromImage();
//                        isFight = true;
//                    }
//
//
//
//                }
//                else if(find) {
//                    this.imageEventText.clearAllDialogs();
//                    this.imageEventText.setDialogOption(OPTIONS[4]);
//                    this.imageEventText.setDialogOption(OPTIONS[3],true);
//
//                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
//                    this.imageEventText.loadImage("images/CharacterImg/Events/Watcher2.png");
//                    fight = true;
//                }
//                else {
//                    this.imageEventText.clearAllDialogs();
//                    if(i==0)
//                        this.imageEventText.setDialogOption(OPTIONS[2]);
//                    this.imageEventText.setDialogOption(OPTIONS[1]);
//                    switch (i) {
//                        case 0:
//                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
//                            this.imageEventText.loadImage(IMG);
//                            if(AbstractDungeon.player instanceof SlugCat){
//                                SlugCat player = (SlugCat)AbstractDungeon.player;
//                                player.forceIncreaseMaxHp(1,true);
//                            }
//                            find = 0==AbstractDungeon.eventRng.random(4);
//                            eattimes++;
//                            break;
//                        case 1:
//                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
//                            this.imageEventText.loadImage(IMG);
//                            leave=true;
//                            break;
//                    }
//                }
//           }
//    }

}
