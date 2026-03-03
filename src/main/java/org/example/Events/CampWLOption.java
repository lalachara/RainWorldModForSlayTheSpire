package org.example.Events;
import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import org.example.Character.*;
import org.example.relics.Liver_SleepPotions;

public class CampWLOption extends AbstractCampfireOption {
    private static final UIStrings Strings = CardCrawlGame.languagePack.getUIString("Liver:Camp");

    //public static final String[] TEXT = uiStrings.TEXT;
    // 引用自定义角色
    private CustomPlayer customPlayer;

    public CampWLOption() {

        this.label = Strings.TEXT[1]; // "休息"
        updateDescription();
        this.img = ImageMaster.loadImage("images/CharacterImg/RainWorld/WorkLevels/Camp.png");

    }

    private void updateDescription() {
        // 自定义描述，显示将要增加的工作等级
        if(AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID))
            this.description = Strings.TEXT[3];
        else
            this.description = Strings.TEXT[0];
    }

//    private int getWorkLevelIncrease() {
//        // 根据游戏进度决定增加量
//        if (AbstractDungeon.actNum >= 3) return 3;
//        if (AbstractDungeon.actNum >= 2) return 2;
//        return 1;
//    }

    @Override
    public void useOption() {
        if (this.usable) {
            // 增加工作等级
            int increase = 1;
            if(AbstractDungeon.player instanceof SlugCat) {
                SlugCat liver = (SlugCat) AbstractDungeon.player;
                liver.hasSleep = true;
               // liver.addMaxWorkLevel(increase);
                liver.addWorkLevel(increase);
                if(liver.hasRelic(Liver_SleepPotions.ID))
                    liver.addMaxWorkLevel(increase);
            }


            // 视觉反馈
            CardCrawlGame.sound.play("SLEEP_BLANKET");
            AbstractDungeon.effectList.add(new CampfireSleepEffect());
            for(int i = 0; i < 30; ++i) {
                AbstractDungeon.topLevelEffects.add(new CampfireSleepScreenCoverEffect());
            }

            ++CardCrawlGame.metricData.campfire_rested;
            CardCrawlGame.metricData.addCampfireChoiceData("REST");
            // 标记已使用
            this.usable = false;
            AbstractDungeon.closeCurrentScreen();


        }
    }
}