package org.example.Events;
import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepScreenCoverEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;

public class CampMaxWLOption extends AbstractCampfireOption {
    private static final UIStrings Strings = CardCrawlGame.languagePack.getUIString("Liver:Camp");

    //public static final String[] TEXT = uiStrings.TEXT;
    // 引用自定义角色
    private CustomPlayer customPlayer;

    public CampMaxWLOption() {

        this.label = Strings.TEXT[4]; // "休息"
        updateDescription();
        this.img = ImageMaster.loadImage("images/CharacterImg/RainWorld/WorkLevels/Camp1.png");
    }

    private void updateDescription() {
        // 自定义描述，显示将要增加的工作等级
        this.description = Strings.TEXT[2];
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
                liver.addMaxWorkLevel(increase);
                AchievementMgr.unlockAchievement(2);
                //liver.addWorkLevel(increase);
                if(AbstractDungeon.id.equals("TheCity"))
                    liver.CampInCity = true;
                if(AbstractDungeon.id.equals("TheBeyond"))
                    liver.CampInBeyond = true;
                if(AbstractDungeon.id.equals("Exordium"))
                    liver.CampInBottom = true;
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