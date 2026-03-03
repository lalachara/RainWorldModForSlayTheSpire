package org.example.Character;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import org.example.ModCore;
import org.example.cards.RedCat.attacks.RedCat_Better;
import org.example.cards.RedCat.attacks.RedCat_Spear;
import org.example.cards.RedCat.attacks.RedCat_Strike;
import org.example.cards.RedCat.powers.RedCat_CantControl;
import org.example.cards.RedCat.skills.RedCat_Corrupt;
import org.example.cards.RedCat.skills.RedCat_Defend;
import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;
import org.example.powers.*;
import org.example.powers.RedCat.*;
import org.example.relics.RedCat_Key;
import org.example.relics.RedCat_Menu;
import org.example.relics.RedCat_RedCatStart;
import org.example.tools.ModConfig;

import java.util.ArrayList;
import java.util.Objects;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;
import static org.example.Character.SlugCat.Enums.RedCat_Color;
import static org.example.Character.SlugCat.PlayerColorEnum.RedCat_CLASS;

public class RedCat extends SlugCat  implements CustomSavable<RedCat.ModSaveDataRedCat> {

    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = "images/CharacterImg/RainWorld/RedCat/camp.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "images/CharacterImg/RainWorld/RedCat/camp.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "images/CharacterImg/RainWorld/RedCat/Died_Lihui.png";
    private static final Texture CORPSE_IMAGE2 = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCat/Died2.png");

    //public static Texture mushroom = new Texture("images/CharacterImg/RainWorld/LiverLiHuiSleep.png");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("RedCat");
    public int var = 0;
    private static int CSworkLevel = 2, // 工作等级
            CSmaxWorkLevel = 4, // 最大工作等级
            CSsleepFood = 6, // 食物点数
            CSmaxFood = 9; // 最大食物点数
    public boolean HasGetCorrupt = false; // 是否变异

    public RedCat(String name) {
        super(name, RedCat_CLASS,CSworkLevel,CSmaxWorkLevel,CSmaxFood,CSsleepFood);


        meow = new Texture("images/CharacterImg/RainWorld/RedCat/LiHui1.png");
        idle = new Texture("images/CharacterImg/RainWorld/RedCat/LiHui.png");
        sleep = new Texture("images/CharacterImg/RainWorld/RedCat/LiHuiSleep.png");


        this.atlas = new TextureAtlas("images/CharacterImg/chara/RedCat/RedPlus.atlas");
        SkeletonJson json = new SkeletonJson(this.atlas); json.setScale(Settings.renderScale );
        json.setScale(Settings.renderScale*0.5F );
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/chara/RedCat/redplus.json"));
        this.skeleton = new Skeleton(skeletonData);
        this.skeleton.setColor(Color.WHITE);

        skeleton.findBone("root").setScale(0.5F);

        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
        this.state.setTimeScale(1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

//        this.atlas = new TextureAtlas(Gdx.files.internal("images/CharacterImg/Monster/IronBird/skeleton.atlas"));
//        SkeletonJson json = new SkeletonJson(this.atlas);
//        json.setScale(Settings.renderScale / 1.8F);
//        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/CharacterImg/Monster/IronBird/skeleton.json"));
//        this.skeleton = new Skeleton(skeletonData); this.skeleton.setColor(Color.WHITE);
//        this.stateData = new AnimationStateData(skeletonData); this.state = new AnimationState(this.stateData);
//        this.state.setTimeScale(1.0F);

        this.initializeClass(
                //"images/CharacterImg/RainWorld/RedCat/LiHui.png", // 人物图片
                null,
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );
        BaseMod.addSaveField("RedCat:ModSaveData", this);

    }
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                11, // 当前血量
                11, // 最大血量
                0, // 初始充能球栏位
                99, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelicShow(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }
    @Override
    public void playDeathAnimation() {
        if(hasPower(RedMushroomBuff.POWER_ID)){
            this.corpseImg = CORPSE_IMAGE2;
        }
        super.playDeathAnimation();
    }


    public ArrayList<String> getStartingDeck() {

        ArrayList<String> retVal = new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            retVal.add(RedCat_Defend.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(RedCat_Strike.ID);

        }
        retVal.add(RedCat_Better.ID);
        retVal.add(RedCat_OriginalCorrupt.ID);
        retVal.add(RedCat_Spear.ID);
        return retVal;
    }
    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(RedCat_Menu.ID);

        retVal.add(RedCat_Key.ID);
        // retVal.add();
        return retVal;
    }
    public ArrayList<String> getStartingRelicShow() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(RedCat_Menu.ID);

        // retVal.add();
        return retVal;
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return RedCat_Color;
    }
    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new RedCat_Better();
    }
    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return ModCore.RedCat_COLOR;
    }
    @Override
    public int getAscensionMaxHPLoss() {
        return 1;
    }
    //碎心图
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart1.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/Heart2.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/RedCatHeart3.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/RedCatHeart4.png"));
        panels.add(new CutscenePanel("images/CharacterImg/RainWorld/RedCatHeart5.png"));
        return panels;
    }
    //角色名
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }
    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new RedCat(this.name);
    }
    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }
    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return ModCore.RedCat_COLOR;
    }
    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }
    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return ModCore.RedCat_COLOR;
    }

//    public void renderUI(SpriteBatch sb){
//        foodPointUI.render(sb);
//        sleepUI.render(sb);
//    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    @Override
    public void applyStartOfTurnPreDrawCards() {
        super.applyStartOfTurnPreDrawCards();
        //addCorrupt(1);
    }


    public void setCorruptAnimation(){
        this.state.setAnimation(1, "xianggu", true);
        CardCrawlGame.sound.play("Liver:WorkError");
    }

    @Override
    public void setidleAnimation() {
        if(!hasPower(RedMushroomBuff.POWER_ID)){
            super.setidleAnimation();
        }
        else {
            setCorruptAnimation();
        }
    }

    public void addCorrupt(int amount) {
         {
             if(hasPower(RestrainPower.POWER_ID)&&amount>0){
                 return;
             }
             if(hasPower(CreateSong.POWER_ID)&&amount>0)
             {
                 CreateSong cs = (CreateSong) this.getPower(CreateSong.POWER_ID);
                 cs.onSpecificTrigger();
             }
             if(hasPower(MixBuff.POWER_ID))
             {
                 if(amount>0)
                    AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(this,this,amount*(this.getPower(MixBuff.POWER_ID)).amount));
                 if(amount<0)
                     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new VigorPower(this,-amount*(this.getPower(MixBuff.POWER_ID)).amount)));
             }
            this.var += amount;
             if(var>0){
                 HasGetCorrupt = true;
             }
            if(var>=5) {
                var = 5;
                bianyi();
            }
            if(var<=0){
                var = 0;
                if(!hasPower(CantControl.POWER_ID))
                    quitbianyi();
            }
        }

    }

    @Override
    public void onVictory() {
        super.onVictory();
    }

    public void bianyi(){


        if(!hasPower(RedMushroomBuff.POWER_ID)){
            setCorruptAnimation();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new RedMushroomBuff(AbstractDungeon.player)));
        }
    }
    public void quitbianyi(){

            if(!hasPower(CantControl.POWER_ID)) {
                super.setidleAnimation();
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, RedMushroomBuff.POWER_ID));
            }else {
                setCorruptAnimation();
            }
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {

        super.useCard(c, monster, energyOnUse);

        if(c.hasTag(Corrupt_Tag)){
            if(!hasPower(RestrainPower.POWER_ID)){
                addCorrupt(1);
            }
        }else if(hasPower(RedMushroomBuff.POWER_ID)){
            addCorrupt(-1);
        }



//        if(hasPower(RedMushroomBuff.POWER_ID)&&c.hasTag(Corrupt_Tag)&&!hasPower(RestrainPower.POWER_ID)&&!hasPower(ExcitePower.POWER_ID)){
//            return;
//        }
//        if(hasPower(RedMushroomBuff.POWER_ID)&&!hasPower(DeepCorrupt.POWER_ID)&&!hasPower(ExcitePower.POWER_ID)){
//                addCorrupt(-1);
//            }
//        if((c.hasTag(Corrupt_Tag)|| Objects.equals(c.cardID, RedCat_Corrupt.ID))&&!hasPower(RestrainPower.POWER_ID)){
//            addCorrupt(1);
//        }
    }



    public static class ModSaveDataRedCat {

        public int workLevel,maxWorkLevel,food,maxFood;
        boolean CampInBottom,CampInCity,CampInBeyond,HasGetCorrupt,hasSleep;

        public ModSaveDataRedCat(int workLevel, int maxWorkLevel, int food, int maxFood,boolean CampInBottom,boolean CampInCity,boolean CampInBeyond, boolean HasGetCorrupt,boolean hasSleep) {
            this.workLevel = workLevel;
            this.maxWorkLevel = maxWorkLevel;
            this.food = food;
            this.maxFood = maxFood;
            this.CampInBeyond = CampInBeyond;
            this.CampInCity = CampInCity;
            this.CampInBottom = CampInBottom;
            this.HasGetCorrupt = HasGetCorrupt;
            this.hasSleep = hasSleep;
        }

    }
    public ModSaveDataRedCat onSave() {
        // 保存当前的工作等级、最大工作等级、食物和最大食物

        return new ModSaveDataRedCat(workLevel, maxWorkLevel, food, maxFood,
                CampInBottom, CampInCity, CampInBeyond,HasGetCorrupt,hasSleep);
    }

    @Override
    public void onLoad(ModSaveDataRedCat modSaveData) {
        if(modSaveData == null) {
            // 如果没有保存数据，则使用默认值
            modSaveData = new ModSaveDataRedCat(CSworkLevel,CSmaxWorkLevel,CSmaxFood,CSsleepFood, false,false,false,false,false);
        }
        // 恢复工作等级、最大工作等级、食物和最大食物
        this.workLevel = modSaveData.workLevel;
        if(ModConfig.realModeEnable&&workLevel>0){
            lossWorkLevel();
        }
        this.maxWorkLevel = modSaveData.maxWorkLevel;
        this.food = modSaveData.food;
        this.maxFood = modSaveData.maxFood;
        //this.var = modSaveData.var;
        //this.isBianyi = modSaveData.isBianyi;
        this.CampInBottom = modSaveData.CampInBottom;
        this.CampInBeyond = modSaveData.CampInBeyond;
        this.CampInCity = modSaveData.CampInCity;
        this.HasGetCorrupt = modSaveData.HasGetCorrupt;
        this.hasSleep = modSaveData.hasSleep;
        // 确保在加载后更新相关状态
        this.healthBarUpdatedEvent();


    }
}
