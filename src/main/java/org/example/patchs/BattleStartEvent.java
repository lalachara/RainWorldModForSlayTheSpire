package org.example.patchs;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.example.Character.*;
import org.example.monsters.MushRoom;
import org.example.powers.ProliferationBuff;


@SpireInitializer
public class BattleStartEvent implements OnStartBattleSubscriber,ISubscriber  {

    public BattleStartEvent() {
        // 订阅战斗开始事件
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new BattleStartEvent();
    }
    public void changebgm(){
        CardCrawlGame.music.silenceBGM();
        CardCrawlGame.sound.playAndLoop("Liver:MushRoom");
    };
    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        // 战斗开始时的逻辑
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                // 你的战斗开始逻辑
                if(AbstractDungeon.player instanceof SlugCat) {
                    ((SlugCat)AbstractDungeon.player).start();

                    if(AbstractDungeon.lastCombatMetricKey!=null&& AbstractDungeon.lastCombatMetricKey.equals("Liver:MushRoom")) {
                        // 如果是香菇战斗，播放特定音乐
                        changebgm();
                    }

                }


                this.isDone = true;
            }
        });

    }


}
