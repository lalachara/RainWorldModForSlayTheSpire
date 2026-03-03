package org.example.Character;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.example.Achievement.AchievementMgr;
import org.example.Actions.ChaseCostUpdateAction;
import org.example.ModCore;
import org.example.cards.Liver.attacks.Liver_Rock;
import org.example.cards.Liver.attacks.Liver_Spear;
import org.example.cards.Liver.attacks.Liver_Strike;
import org.example.cards.Liver.attacks.*;
import org.example.cards.Liver.skills.Liver_Defend;
import org.example.relics.*;
import org.example.tools.ModConfig;

import java.util.ArrayList;


import static org.example.Character.SlugCat.PlayerColorEnum.Liver_CLASS;


// 继承CustomPlayer类
public class Liver extends SlugCat implements CustomSavable<Liver.ModSaveData> {
    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = "images/CharacterImg/RainWorld/Camp1.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "images/CharacterImg/RainWorld/Camp1.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "images/CharacterImg/RainWorld/Died_Lihui.png";
//    // 战斗界面左下角能量图标的每个图层
//    private static final String[] ORB_TEXTURES;
//    // 每个图层的旋转速度
//    private static final float[] LAYER_SPEED ;
    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Liver");
//    public FoodPointUI foodPointUI = new FoodPointUI(null);
//    public SleepUI sleepUI = new SleepUI(null);
//    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("Liver:WorkLevel");

    //自定义变量


    public Liver(String name) {
        super(name, Liver_CLASS,2,4,7,4);
        this.atlas = new TextureAtlas("images/CharacterImg/chara/Liver/WhitePlus.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        json.setScale(Settings.renderScale*0.5F );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/chara/Liver/whiteplus.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        this.initializeClass(
                null,
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量

        );

        BaseMod.addSaveField("Liver:ModSaveData", this);
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {

        ArrayList<String> retVal = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            retVal.add(Liver_Strike.ID);
        }
        for (int i = 0; i < 5; i++) {
            retVal.add(Liver_Defend.ID);

        }
        //retVal.add(Liver_Sleep.ID);
        retVal.add(Liver_Rock.ID);
        retVal.add(Liver_Spear.ID);
        return retVal;
    }
    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Liver_RelicFruit.ID);

        // retVal.add();
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                9, // 当前血量
                9, // 最大血量
                0, // 初始充能球栏位
                99, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.Liver_Color;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Liver_Rock();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return ModCore.Liver_COLOR;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 1;
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart1.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart2.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart3.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart4.png"));
        return panels;
    }


    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new Liver(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return ModCore.Liver_COLOR;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return ModCore.Liver_COLOR;
    }



    public static class ModSaveData {

        public int workLevel,maxWorkLevel,food,maxFood,kills;
        boolean CampInBottom,CampInCity,CampInBeyond,hasSleep;

        public ModSaveData(int workLevel, int maxWorkLevel, int food, int maxFood, int kills,boolean CampInBottom,boolean CampInCity,boolean CampInBeyond,boolean hasSleep) {
            this.workLevel = workLevel;
            this.maxWorkLevel = maxWorkLevel;
            this.food = food;
            this.maxFood = maxFood;
            this.kills = kills;
            this.CampInBeyond = CampInBeyond;
            this.CampInCity = CampInCity;
            this.CampInBottom = CampInBottom;
            this.hasSleep = hasSleep;
        }

    }
    @Override
    public ModSaveData onSave() {
        // 保存当前的工作等级、最大工作等级、食物和最大食物
        return new ModSaveData(workLevel, maxWorkLevel, food, maxFood, kills,CampInBottom,CampInCity,CampInBeyond,hasSleep);
    }

    @Override
    public void onLoad(ModSaveData modSaveData) {
        if(modSaveData == null) {
            // 如果没有保存数据，则使用默认值
            modSaveData = new ModSaveData(2, 4, 0, 9, 0,false,false,false,false);
        }
        // 恢复工作等级、最大工作等级、食物和最大食物
        this.workLevel = modSaveData.workLevel;
        if(ModConfig.realModeEnable&&workLevel>0){
            lossWorkLevel();
        }
        this.maxWorkLevel = modSaveData.maxWorkLevel;
        this.food = modSaveData.food;
        this.maxFood = modSaveData.maxFood;
        this.kills = modSaveData.kills;
        this.CampInBottom = modSaveData.CampInBottom;
        this.CampInBeyond = modSaveData.CampInBeyond;
        this.CampInCity = modSaveData.CampInCity;
        this.hasSleep = modSaveData.hasSleep;

        // 确保在加载后更新相关状态
        this.healthBarUpdatedEvent();

    }

}
