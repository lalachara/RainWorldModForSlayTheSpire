package org.example.Character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.neow.NeowReward;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import org.example.Achievement.AchievementMgr;
import org.example.Actions.ChaseCostUpdateAction;
import org.example.Actions.WorkRewardAction;
import org.example.UIs.UIController;
import org.example.cards.Liver.others.Liver_stubborn;
import org.example.potions.WorkFlower;
import org.example.powers.*;
import org.example.powers.RedCat.RedMushroomBuff;
import org.example.relics.*;
import org.example.tools.ModConfig;
import org.example.tools.Tools;

import java.util.Iterator;

public abstract class SlugCat extends CustomPlayer{
    private static final String[] ORB_TEXTURES;
    private static final float[] LAYER_SPEED ;

    //public FoodPointUI foodPointUI = new FoodPointUI();
   // public SleepUI sleepUI = new SleepUI();
    public int workLevel,maxWorkLevel = 4,food,maxFood=7,sleepfood=4;
    public int kills = 0;
    private static Hitbox workLevelHitbox;
    //private WorkLevelIndicator workLevelIndicator;
    private final float X = 94* Settings.scale;
    private final float Y = 340* Settings.scale;
    private int meowtime = 0;
    public UIController uiController;
    public boolean onclick = false;
    protected   Texture meow = new Texture("images/CharacterImg/RainWorld/LiverLiHui1.png");
    protected  Texture idle = new Texture("images/CharacterImg/RainWorld/LiverLiHui.png");
    protected  Texture sleep = new Texture("images/CharacterImg/RainWorld/LiverLiHuiSleep.png");
    //private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("Liver:WorkLevel");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Liver");
    public boolean CampInBottom = false,CampInCity = false,CampInBeyond = false;
    public boolean hasSleep = false;
    public int  eattimes = 0;
    public SlugCat(String name,PlayerClass playerClass,int workLevel,int maxWorkLevel,int maxFood,int sleepfood) {
        super(name, playerClass, ORB_TEXTURES, "images/CharacterImg/RainWorld/orbs/vfx.png", LAYER_SPEED, null, null);
        // 检查 hb 是否为 null
        if (this.hb == null) {
            this.hb = new Hitbox(0, 0, 110F* Settings.scale, 110F* Settings.scale); // 初始化默认 Hitbox
        }
        // 检查图片是否加载成功
        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("ExampleModResources/img/char/character.atlas", "ExampleModResources/img/char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);

        this.workLevel = workLevel; // 初始业力设置
        this.maxWorkLevel = maxWorkLevel; // 初始最大业力设置
        this.food = 0; // 初始食物设置
        this.maxFood = maxFood; // 初始最大食物设置
        this.sleepfood = sleepfood; // 初始睡眠食物设置
        uiController = new UIController(this);

        AchievementMgr.init(ModConfig.AchievementsString);

        // 初始业力设置
//        float hitboxWidth = 110F; // 弹药栏宽度
//        float hitboxHeight = 110F; // 弹药栏高度
//        float hitboxX = X; // 弹药栏 X 坐标
//        float hitboxY = Y; // 弹药栏 Y 坐标

        //workLevelHitbox = new Hitbox(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
       // workLevelIndicator = new WorkLevelIndicator();


//        workLevel = 2;
//        food = 0;

//        maxHealth = 9;
//        BaseMod.addSaveField("Rainworld:ModSaveData", this);
    }
    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        if (c.type == AbstractCard.CardType.ATTACK)
            OnUseAckCard();

    }
    public void OnUseAckCard(){

        AbstractDungeon.actionManager.addToBottom(new ChaseCostUpdateAction());
    }

    public static void initialize() {
        // 这个方法是用来初始化人物的，必须要有

    }
//    public abstract ArrayList<String> getStartingDeck();
//    public abstract ArrayList<String> getStartingRelics();
//    public abstract CharSelectInfo getLoadout();
    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("MEOW", MathUtils.random(-0.15F, 0.15F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MEOW";
    }
    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }



    public static class PlayerLibraryEnum {
        // ***将CardColor和LibraryType的变量名改为你的角色的颜色名称，确保不会与其他mod冲突***
        // ***并且名称需要一致！***
        // 这个变量未被使用（呈现灰色）是正常的
        @SpireEnum
        public static CardLibrary.LibraryType RedCat_Color;
        @SpireEnum
        public static CardLibrary.LibraryType Liver_Color;
    }
    public static class Enums {
        @SpireEnum(name = "Liver_Color")
        public static AbstractCard.CardColor Liver_Color;
        @SpireEnum(name = "RedCat_Color")
        public static AbstractCard.CardColor RedCat_Color;
        @SpireEnum(name = "Spear_TAG")
        public static AbstractCard.CardTags Spear_TAG;
        @SpireEnum(name = "Treasure_TAG")
        public static AbstractCard.CardTags Treasure_TAG;
        @SpireEnum(name = "TreasureSpear_TAG")
        public static AbstractCard.CardTags TreasureSpear_TAG;
        @SpireEnum(name = "CantRemoved_TAG")
        public static AbstractCard.CardTags CantRemoved_TAG;
        @SpireEnum(name = "Corrupt_Tag")
        public static AbstractCard.CardTags Corrupt_Tag;
        @SpireEnum(name = "Immunity_Tag")
        public static AbstractCard.CardTags Immunity_Tag;
        @SpireEnum(name = "CantCorrupt_Tag")
        public static AbstractCard.CardTags CantCorrupt_Tag;
        @SpireEnum(name = "Corrupter_Tag")
        public static AbstractCard.CardTags CatStrike_Tag;
        @SpireEnum(name = "WorkReward_Tag")
        public static AbstractCard.CardTags WorkReward_Tag;
        @SpireEnum(name = "AddWorkLevel")
        public static NeowReward.NeowRewardType AddWorkLevel;
        @SpireEnum(name = "AddMaxWorkLevel")
        public static NeowReward.NeowRewardType AddMaxWorkLevel;

    }

    //@Override
//    public void damage(DamageInfo info){
//        int damageAmount = info.output;
//        boolean hadBlock = true;
//        if (this.currentBlock == 0) {
//            hadBlock = false;
//        }
//
//        if (damageAmount < 0) {
//            damageAmount = 0;
//        }
//
//        if (damageAmount > 1 && this.hasPower("IntangiblePlayer")) {
//            damageAmount = 1;
//        }
//
//        //damageAmount = TempHpHelper.handleTempHpDamage(player, null, damageAmount);
//
//
//        damageAmount = this.decrementBlock(info, damageAmount);
//
//        Iterator var4;
//        AbstractRelic r;
//        if (info.owner == this) {
//            for(var4 = this.relics.iterator(); var4.hasNext(); damageAmount = r.onAttackToChangeDamage(info, damageAmount)) {
//                r = (AbstractRelic)var4.next();
//            }
//        }
//
//        AbstractPower p;
//        if (info.owner != null) {
//            for(var4 = info.owner.powers.iterator(); var4.hasNext(); damageAmount = p.onAttackToChangeDamage(info, damageAmount)) {
//                p = (AbstractPower)var4.next();
//            }
//        }
//
//        for(var4 = this.relics.iterator(); var4.hasNext(); damageAmount = r.onAttackedToChangeDamage(info, damageAmount)) {
//            r = (AbstractRelic)var4.next();
//        }
//
//        for(var4 = this.powers.iterator(); var4.hasNext(); damageAmount = p.onAttackedToChangeDamage(info, damageAmount)) {
//            p = (AbstractPower)var4.next();
//        }
//
//        if (info.owner == this) {
//            var4 = this.relics.iterator();
//
//            while(var4.hasNext()) {
//                r = (AbstractRelic)var4.next();
//                r.onAttack(info, damageAmount, this);
//            }
//        }
//
//        if (info.owner != null) {
//            var4 = info.owner.powers.iterator();
//
//            while(var4.hasNext()) {
//                p = (AbstractPower)var4.next();
//                p.onAttack(info, damageAmount, this);
//            }
//
//            for(var4 = this.powers.iterator(); var4.hasNext(); damageAmount = p.onAttacked(info, damageAmount)) {
//                p = (AbstractPower)var4.next();
//            }
//
//            for(var4 = this.relics.iterator(); var4.hasNext(); damageAmount = r.onAttacked(info, damageAmount)) {
//                r = (AbstractRelic)var4.next();
//            }
//        }
//        for(var4 = this.relics.iterator(); var4.hasNext(); damageAmount = r.onLoseHpLast(damageAmount)) {
//            r = (AbstractRelic)var4.next();
//        }
//
//        this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
//        if (damageAmount > 0) {
//            for(var4 = this.powers.iterator(); var4.hasNext(); damageAmount = p.onLoseHp(damageAmount)) {
//                p = (AbstractPower)var4.next();
//            }
//
//            var4 = this.relics.iterator();
//
//            while(var4.hasNext()) {
//                r = (AbstractRelic)var4.next();
//                r.onLoseHp(damageAmount);
//            }
//
//            var4 = this.powers.iterator();
//
//            while(var4.hasNext()) {
//                p = (AbstractPower)var4.next();
//                p.wasHPLost(info, damageAmount);
//            }
//
//            var4 = this.relics.iterator();
//
//            while(var4.hasNext()) {
//                r = (AbstractRelic)var4.next();
//                r.wasHPLost(damageAmount);
//            }
//
//            if (info.owner != null) {
//                var4 = info.owner.powers.iterator();
//
//                while(var4.hasNext()) {
//                    p = (AbstractPower)var4.next();
//                    p.onInflictDamage(info, damageAmount, this);
//                }
//            }
//
//            if (info.owner != this) {
//                this.useStaggerAnimation();
//            }
//
//            if (info.type == DamageInfo.DamageType.HP_LOSS) {
//                GameActionManager.hpLossThisCombat += damageAmount;
//            }
//
//            GameActionManager.damageReceivedThisTurn += damageAmount;
//            GameActionManager.damageReceivedThisCombat += damageAmount;
//            this.currentHealth -= damageAmount;
//            if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
//                this.updateCardsOnDamage();
//                ++this.damagedThisCombat;
//            }
//
//            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
//            if (this.currentHealth <= 0) {
//                if(workLevel>0||(getPower(WorkLevelLockBuff.POWER_ID)!=null)){
//                    relive();
//                    return;
//                }else{
//                    CardCrawlGame.sound.stop("Liver:MushRoom");
//                    CardCrawlGame.music.unsilenceBGM();
//                    this.currentHealth = 0;
//                }
//
//            } else if (this.currentHealth < this.maxHealth / 4) {
//                AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F)));
//            }
//
//            this.healthBarUpdatedEvent();
//            if ((float)this.currentHealth <= (float)this.maxHealth / 2.0F && !this.isBloodied) {
//                this.isBloodied = true;
//                var4 = this.relics.iterator();
//
//                while(var4.hasNext()) {
//                    r = (AbstractRelic)var4.next();
//                    if (r != null) {
//                        r.onBloodied();
//                    }
//                }
//            }
//
//            if (this.currentHealth < 1) {
//
//
//                if (!this.hasRelic("Mark of the Bloom")) {
//                    if (this.hasPotion("FairyPotion")) {
//                        var4 = this.potions.iterator();
//
//                        while(var4.hasNext()) {
//                            AbstractPotion p2 = (AbstractPotion)var4.next();
//                            if (p2.ID.equals("FairyPotion")) {
//                                p2.flash();
//                                this.currentHealth = 0;
//                                p2.use(this);
//                                AbstractDungeon.topPanel.destroyPotion(p2.slot);
//                                return;
//                            }
//                        }
//                    } else if (this.hasRelic("Lizard Tail") && ((LizardTail)this.getRelic("Lizard Tail")).counter == -1) {
//                        this.currentHealth = 0;
//                        this.getRelic("Lizard Tail").onTrigger();
//                        return;
//                    }
//                }
//                if(hasPower(BorrowLifeBuff.POWER_ID)){
//                    getPower(BorrowLifeBuff.POWER_ID).onSpecificTrigger();
//                    return;
//                }
//
//                this.isDead = true;
//                AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
//                this.currentHealth = 0;
//                if (this.currentBlock > 0) {
//                    this.loseBlock();
//                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
//                }
//            }
//        } else if (this.currentBlock > 0) {
//            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, uiStrings.TEXT[0]));
//        } else if (hadBlock) {
//            AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, uiStrings.TEXT[0]));
//            AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0F + BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0F + BLOCK_ICON_Y));
//        } else {
//            AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
//        }
//    }
    private void updateCardsOnDamage() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            Iterator var1 = this.hand.group.iterator();

            AbstractCard c;
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.tookDamage();
            }

            var1 = this.discardPile.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.tookDamage();
            }

            var1 = this.drawPile.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.tookDamage();
            }
        }

    }
    public void CheckSleep(int damageAmount){
        if(damageAmount >0&&!AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID)){
            if(this.hasPower(SleepBuff.POWER_ID)){
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this,this,SleepBuff.POWER_ID));
                this.setidleAnimation();

            }
        }
    }

    public void SaveGame(){
        if (!CardCrawlGame.loadingSave && !AbstractDungeon.loading_post_combat) {
            SaveFile saveFile = new SaveFile(SaveFile.SaveType.ENTER_ROOM);
            saveFile.card_seed_count = AbstractDungeon.cardRng.counter;
            saveFile.card_random_seed_randomizer = AbstractDungeon.cardBlizzRandomizer;
            if (AbstractDungeon.getCurrRoom().combatEvent) {
                --saveFile.event_seed_count;
            }
            SaveAndContinue.save(saveFile);
        }
    }

    public void behit(){



        if(!hasPower(RedMushroomBuff.POWER_ID) &&(workLevel>0||(getPower(WorkLevelLockBuff.POWER_ID)!=null))){
            relive();
            return;
        }else if(hasPower(BorrowLifeBuff.POWER_ID)){
                getPower(BorrowLifeBuff.POWER_ID).onSpecificTrigger();
                currentHealth = 1;
            }
        else {
            CardCrawlGame.sound.stop("Liver:MushRoom");
            CardCrawlGame.music.unsilenceBGM();
            this.currentHealth = 0;
            setidleAnimation();
        }
    }
    public void relive(){
        AchievementMgr.unlockAchievement(1);
        if (this.hasPower(WorkLevelLockBuff.POWER_ID)) {
            this.getPower(WorkLevelLockBuff.POWER_ID).onSpecificTrigger();
        }
        else{

            lossWorkLevel();
            if(hasPower(BorrowLifeBuff.POWER_ID)){
                getPower(BorrowLifeBuff.POWER_ID).onSpecificTrigger();
            }
            if(hasRelic(Liver_MoonSkin.ID)&&AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(this,20));
            }
        }
        currentHealth = maxHealth;
        this.healthBarUpdatedEvent();
//    if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
//        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this,this,new ReLiveBuff(this,1),1));
//    this.healthBarUpdatedEvent();
    }
    public void increaseMaxHp(int amount, boolean showEffect) {
        if (!Settings.isEndless || !AbstractDungeon.player.hasBlight("FullBelly")) {
            addFood(amount);
//            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Liver_HeartFear(), Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
            this.heal(amount, true);
            this.healthBarUpdatedEvent();
        }
    }
    public int getFood() {
        return food;
    }
    public void forceIncreaseMaxHp(int amount, boolean showEffect){
        super.increaseMaxHp(amount,showEffect);
    }
    public void addFood(int amount){
        CardCrawlGame.sound.play("Liver:FoodUp");
        boolean full = food==maxFood;
        food+=amount;
        if(!full)
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.hb.cX - this.animX, this.hb.cY, characterStrings.TEXT[2] + Integer.toString(amount), Settings.GREEN_TEXT_COLOR));
        if(food>maxFood)
        {
            int damage = food-maxFood;
            if(hasRelic(Liver_LongLegMushroom.ID)&&AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                boolean isdamageself = false;
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped()&&mo.currentHealth>0) {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(mo,new DamageInfo(this,damage, DamageInfo.DamageType.THORNS)));
                        isdamageself = true;
                    }
                }
                if(isdamageself){
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(this,new DamageInfo(this,damage, DamageInfo.DamageType.THORNS)));
                }
            }
            if(hasPower(FatWorldBuff.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,getPower(FatWorldBuff.POWER_ID).amount),getPower(FatWorldBuff.POWER_ID).amount));
            }
        }
        food = Math.min(food,maxFood);
    }
    @Override
    public void onVictory() {
        super.onVictory();
        uiController.start();
        this.state.setAnimation(1, "idle", true);
        CardCrawlGame.sound.stop("Liver:MushRoom");
        CardCrawlGame.music.unsilenceBGM();
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss&&this instanceof Liver) {
            // 增加业力上限
            this.addMaxWorkLevel(1);
        }
    }
    public void addWorkLevel(int amount){
        if(!hasRelic(MarkOfTheBloom.ID)){
            if(!this.hasPower(WorkErrorBuff.POWER_ID)){
                CardCrawlGame.sound.play("Liver:WorkErrorUp");
                this.workLevel+=amount;
                if(workLevel> maxWorkLevel||(workLevel==maxWorkLevel&& Tools.hasCard(Liver_stubborn.ID))){
                    if(hasPower(Inherent.POWER_ID)){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,1),1));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new DexterityPower(this,1),1));
                    }
                }
            }
//            if(hasPower(CatComingBuff.POWER_ID)){
//                getPower(CatComingBuff.POWER_ID).onSpecificTrigger();
//            }
            this.currentHealth = this.maxHealth;
            this.workLevel = Math.min(workLevel,Tools.hasCard(Liver_stubborn.ID)?maxWorkLevel-1:maxWorkLevel);
            this.healthBarUpdatedEvent();
        }
    }
    public void addMaxWorkLevel(int amount){
        maxWorkLevel+=amount;
        if(maxWorkLevel>9){
            maxWorkLevel = 9;
        }
        if(workLevel>(Tools.hasCard(Liver_stubborn.ID)?maxWorkLevel-1:maxWorkLevel)){
            workLevel = Tools.hasCard(Liver_stubborn.ID)?maxWorkLevel-1:maxWorkLevel;
        }
        currentHealth = maxHealth;
    }
    public void lossWorkLevel(){
        if (this.hasPower(WorkLevelLockBuff.POWER_ID)) {
            this.getPower(WorkLevelLockBuff.POWER_ID).onSpecificTrigger();
        }else if(workLevel>0){

            if(hasRelic(Liver_FlowerAgain.ID)){
                if(getRelic(Liver_FlowerAgain.ID).counter!=0)
                    getRelic(Liver_FlowerAgain.ID).counter=0;
                AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(new WorkFlower()));
            }

            workLevel--;
            if(hasPower(Inherent.POWER_ID)){
                Inherent P =  (Inherent) getPower(Inherent.POWER_ID);
                P.addWorkLevelLossCount();
                AbstractDungeon.actionManager.addToBottom(new WorkRewardAction());
            }
            CardCrawlGame.sound.play("Liver:WorkErrorDown");

        }else if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this,new DamageInfo(this,9999, DamageInfo.DamageType.NORMAL)));
        }else {
            AbstractDungeon.player.damage(new DamageInfo(null, 9999, DamageInfo.DamageType.HP_LOSS));

        }

    }
    public void start(){
        // AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(new WorkFlower()));
        if(ModConfig.realModeEnable)
            SaveGame();
        this.addPower(new Inherent(this));
        if(!hasRelic(RedCat_mushroom.ID))
            setidleAnimation();
        uiController.start();
    }

    @Override
    public void applyStartOfTurnPreDrawCards() {
        super.applyStartOfTurnPreDrawCards();
        uiController.turnStart();
    }

    @Override
    public void update() {
        super.update();
        uiController.update();
        //workLevelHitbox.update(); // 更新 Hitbox 状态
        if(!hasPower(SleepBuff.POWER_ID)&&!hasPower(OverSleepBuff.POWER_ID)){
            if (this.hb.hovered && InputHelper.justClickedLeft) {
                this.onClick();
                onclick = true;
            }else
                onclick = false;
        }

    }
    private void onClick(){
        if(!hasPower(RedMushroomBuff.POWER_ID)&&!hasPower(SleepBuff.POWER_ID)){
            if(hasPower(Inherent.POWER_ID))
                if(((Inherent)getPower(Inherent.POWER_ID)).wawawa)
                    CardCrawlGame.sound.play("Liver:WAAAAAA");
                else
                    CardCrawlGame.sound.playA("MEOW", MathUtils.random(-0.15F, 0.15F));
            AchievementMgr.unlockAchievement(21);
        }



    }
    @Override
    public void render(SpriteBatch sb) {

        if(hasPower(SleepBuff.POWER_ID)||hasPower(OverSleepBuff.POWER_ID)){
            //super.img = sleep;
        }else if(hasPower(RedMushroomBuff.POWER_ID)){
            //super.img = RedCat.mushroom;
        }
        else {
            if(meowtime!=0){
                meowtime--;
                if(meowtime==0){
                    //super.img = idle;

                    //this.skeleton.setToSetupPose();
                }
            }else if(hasPower(Inherent.POWER_ID)){
                if(onclick){
                    //super.img = meow;
                    this.state.setAnimation(1, "meow", false);
                    this.state.addAnimation(0, "idle", true, 0f);


                    meowtime = 30;
                }
//                else if(currentHealth>0)
//                    super.img = idle;

            }
        }
        //System.out.println((this.atlas != null)?"has atlas":"no atlas");
        super.render(sb);
    }
    public void setSleepAnimation(){
        this.state.setAnimation(1, "sleep", true);
    }
    public void setidleAnimation() {
        this.state.setAnimation(1, "idle", true);
    }
    public void renderUI(SpriteBatch sb){

        uiController.render(sb);
    }
    public void sleep(boolean isover){
        setSleepAnimation();
        int cost = sleepfood;
        if(hasRelic(Liver_SleepPotions.ID))
            cost = sleepfood-1;
        if(isover)
            cost = maxFood;
        if(food>=cost)
        {
            food -= cost;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new SleepBuff(this,1)));
        }else if(!isover){
            food = 0;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new OverSleepBuff(this,1)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new OverSleepDeBuff(this,1)));

        }
        hasSleep = true;
    }
//    public void renderWorkLevelBar(SpriteBatch sb) {
//        workLevelIndicator.update(this.workLevel);
//        workLevelIndicator.render(sb);
//
//        workLevelHitbox.move(X+55F, Y+55F);
//        workLevelHitbox.render(sb);
//
//        if (workLevelHitbox.hovered) {
//            TipHelper.renderGenericTip(
//                    InputHelper.mX + 20.0F * Settings.scale,
//                    InputHelper.mY,
//                    UIs.TEXT[0],
//                    UIs.TEXT[1] + (workLevel+1) +"/"+(Tools.hasCard(Liver_stubborn.ID)?maxWorkLevel:(maxWorkLevel+1)
//                    ));
//
//        }
//    }
    static {
        String[] arrayOfString = new String[11];
        arrayOfString[0] = "images/CharacterImg/RainWorld/EPanel/layer5.png";
        arrayOfString[1] = "images/CharacterImg/RainWorld/EPanel/layer4.png";
        arrayOfString[2] = "images/CharacterImg/RainWorld/EPanel/layer3.png";
        arrayOfString[3] = "images/CharacterImg/RainWorld/EPanel/layer2.png";
        arrayOfString[4] = "images/CharacterImg/RainWorld/EPanel/layer1.png";
        arrayOfString[5] = "images/CharacterImg/RainWorld/EPanel/layer0.png";
        arrayOfString[6] = "images/CharacterImg/RainWorld/EPanel/layer5d.png";
        arrayOfString[7] = "images/CharacterImg/RainWorld/EPanel/layer4d.png";
        arrayOfString[8] = "images/CharacterImg/RainWorld/EPanel/layer3d.png";
        arrayOfString[9] = "images/CharacterImg/RainWorld/EPanel/layer2d.png";
        arrayOfString[10] = "images/CharacterImg/RainWorld/EPanel/layer1d.png";


        arrayOfString[3] = "images/CharacterImg/null.png";
        arrayOfString[4] = "images/CharacterImg/null.png";
        arrayOfString[9] = "images/CharacterImg/null.png";
        arrayOfString[10] = "images/CharacterImg/null.png";
        ORB_TEXTURES = arrayOfString;
        float[] arrayOfFloat = new float[10]; arrayOfFloat[0] = -40.0F; arrayOfFloat[1] = -32.0F; arrayOfFloat[2] = 20.0F; arrayOfFloat[3] = -20.0F; arrayOfFloat[4] = 0.0F; arrayOfFloat[5] = -10.0F; arrayOfFloat[6] = -8.0F; arrayOfFloat[7] = 5.0F; arrayOfFloat[8] = -5.0F; arrayOfFloat[9] = 0F; LAYER_SPEED = arrayOfFloat;

    }
    public static class PlayerColorEnum {
        // 修改为你的颜色名称，确保不会与其他mod冲突
        @SpireEnum
        public static PlayerClass RedCat_CLASS;
        @SpireEnum
        public static PlayerClass Liver_CLASS;
        @SpireEnum
        public static PlayerClass Monk_CLASS;

        // ***将CardColor和LibraryType的变量名改为你的角色的颜色名称，确保不会与其他mod冲突***
        // ***并且名称需要一致！***
    }


}
