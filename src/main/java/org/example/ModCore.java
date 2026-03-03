 package org.example;

 import basemod.BaseMod;
 import basemod.eventUtil.AddEventParams;
 import basemod.eventUtil.EventUtils;
 import basemod.helpers.RelicType;
 import basemod.interfaces.*;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.*;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.monsters.MonsterGroup;
 import com.megacrit.cardcrawl.monsters.MonsterInfo;
 import org.example.Achievement.AchievementMgr;
 import org.example.Character.*;

 import org.example.Character.RedCat;
 import org.example.Events.*;
 import org.example.monsters.*;
 import org.example.potions.Chuang;
 import org.example.potions.MilkTea;
 import org.example.potions.WorkFlower;
 import org.example.relics.*;
 import org.example.tools.InitCards;
 import org.example.tools.ModConfig;


 import java.nio.charset.StandardCharsets;

 import static basemod.BaseMod.gson;
 import static org.example.Character.SlugCat.Enums.Liver_Color;
 import static org.example.Character.SlugCat.Enums.RedCat_Color;
 import static org.example.Character.SlugCat.PlayerColorEnum.Liver_CLASS;
 import static org.example.Character.SlugCat.PlayerColorEnum.RedCat_CLASS;


 @SpireInitializer
    public class ModCore implements EditCardsSubscriber, PostUpdateSubscriber, RenderSubscriber,PostInitializeSubscriber,AddAudioSubscriber,EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber {
    private static final String MY_CHARACTER_BUTTON = "images/CharacterImg/RainWorld/Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "images/CharacterImg/RainWorld/MenuBg1.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "images/CharacterImg/RainWorld/CardAttack512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "images/CharacterImg/RainWorld/CardPower512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "images/CharacterImg/RainWorld/CardSkill512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "images/CharacterImg/RainWorld/orbs/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "images/CharacterImg/RainWorld/CardAttack1024.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "images/CharacterImg/RainWorld/CardPower1024.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "images/CharacterImg/RainWorld/CardSkill1024.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "images/CharacterImg/RainWorld/orbs/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "images/CharacterImg/RainWorld/orbs/cost_orb.png";

    public static final Color Liver_COLOR = new Color(255f, 255F, 255F , 1F);
     public static final Color RedCat_COLOR = new Color(234f/255f, 13F/255f, 5F/255f , 1F);

       public ModCore() {
/*  53 */     BaseMod.subscribe((ISubscriber)this);
           ModConfig.initModSettings();
/*  54 */     BaseMod.addColor(Liver_Color, Liver_COLOR, Liver_COLOR, Liver_COLOR, Liver_COLOR, Liver_COLOR, Liver_COLOR, Liver_COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENEYGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
            BaseMod.addColor(RedCat_Color, RedCat_COLOR, RedCat_COLOR, RedCat_COLOR, RedCat_COLOR, RedCat_COLOR, RedCat_COLOR, RedCat_COLOR, "images/CharacterImg/RainWorld/RedCat/CardAttack512.png","images/CharacterImg/RainWorld/RedCat/CardSkill512.png","images/CharacterImg/RainWorld/RedCat/CardPower512.png", ENEYGY_ORB, "images/CharacterImg/RainWorld/RedCat/CardAttack1024.png", "images/CharacterImg/RainWorld/RedCat/CardSkill1024.png", "images/CharacterImg/RainWorld/RedCat/CardPower1024.png", BIG_ORB, SMALL_ORB);

           // 字符串填不会和其他SaveField重名的id


       }
   public static void initialize() {
/*  57 */     new ModCore();

   }



   public void receiveEditCharacters() {
//       BaseMod.addCharacter(
//               new wishdell(CardCrawlGame.playerName),
//               MY_CHARACTER_BUTTON,
//               MY_CHARACTER_PORTRAIT,
//               wishdell.Enums.MY_CHARACTER
//       );

       AbstractPlayer p = (AbstractPlayer)new Liver(CardCrawlGame.playerName);
       BaseMod.addCharacter(p, MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, Liver_CLASS);
       p = (AbstractPlayer)new RedCat(CardCrawlGame.playerName);
       BaseMod.addCharacter(p, "images/CharacterImg/RainWorld/RedCat/Button.png", "images/CharacterImg/RainWorld/RedCat/MenuBg.png", RedCat_CLASS);

       BaseMod.subscribe(new Liver(CardCrawlGame.playerName));
       BaseMod.subscribe(new RedCat(CardCrawlGame.playerName));

   }
     public void receiveEditPotions() {
         BaseMod.addPotion(WorkFlower.class, Color.CLEAR, (Color)null, (Color)null, WorkFlower.POTION_ID, Liver_CLASS);
         BaseMod.addPotion(Chuang.class, Color.CLEAR, (Color)null, (Color)null, Chuang.POTION_ID, Liver_CLASS);
         BaseMod.addPotion(MilkTea.class, Color.CLEAR, (Color)null, (Color)null, MilkTea.POTION_ID, Liver_CLASS);


  }
//   public void receiveEditKeywords() {
//     String lang;
///*  67 */     Gson gson = new Gson();
//
///*  69 */     if (Settings.language == Settings.GameLanguage.ZHS) {
///*  70 */       lang = "ZHS";
//     } else {
///*  72 */       lang = "ENG";
//     }
//
//
///*  76 */     String json = Gdx.files.internal(MyModHelper.makeLocalizationPath(lang, "keywords")).readString(String.valueOf(StandardCharsets.UTF_8));
///*  77 */     Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
///*  78 */     if (keywords != null) {
///*  79 */       for (Keyword keyword : keywords)
//       {
///*  81 */         BaseMod.addKeyword("logosmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
//       }
//     }
//   }


   public void receiveEditCards() {

       InitCards.InitLiverCards();
//       BaseMod.addCard(new Charas_SwitchSite());
//        BaseMod.addCard(new Wishdell_Shieldhit());
//        BaseMod.addCard(new Wishdell_Rain());


   }

     private void initializeEvents() {
//         BaseMod.addEvent((new AddEventParams.Builder("Liver:Test", TestEvent.class))
//             .eventType(EventUtils.EventType.NORMAL)
//            .dungeonID("TheCity")
//            .dungeonID("TheBeyond")
//           .endsWithRewardsUI(false)
//            .spawnCondition(() -> (AbstractDungeon.player instanceof SlugCat))
//             .create());

         if(ModConfig.specialEventEnable) {


             BaseMod.addEvent((new AddEventParams.Builder("Liver:Moon", MeetMoon.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheCity")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof Liver) && !AbstractDungeon.player.hasRelic(Liver_MoonSkin.ID))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("Liver:FiveRock", FiveRock.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("Exordium")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof SlugCat) && !AbstractDungeon.player.hasRelic(Liver_TalkSign.ID))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("Liver:FruitTree", FruitTree.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheBeyond")
                     .dungeonID("TheCity")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof SlugCat))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("Liver:GoldSea", GoldSea.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheBeyond")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof Liver))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("Liver:RainDeer", RainDeer.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheBeyond")
                     .dungeonID("TheCity")
                     .dungeonID("Exordium")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof SlugCat))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("Rainworld:Watcher", Watcher.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheBeyond")
                     .endsWithRewardsUI(true)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof SlugCat))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("RedCat:Moon",MeetMoonRedCat.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheCity")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof RedCat) && !AbstractDungeon.player.hasRelic(Liver_MoonSkin.ID))
                     .create());
             BaseMod.addEvent((new AddEventParams.Builder("RedCat:Fucycle",Fucycle.class))
                     .eventType(EventUtils.EventType.NORMAL)
                     .dungeonID("TheBeyond")
                     .endsWithRewardsUI(false)
                     .spawnCondition(() -> (AbstractDungeon.player instanceof RedCat) )
                     .create());

         }


     }

   public void receiveEditRelics() {
       BaseMod.addRelicToCustomPool(new Liver_RelicFruit(),Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_Tubularis(), Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_LongLegMushroom(), Liver_Color);
       BaseMod.addRelic(new Liver_BatteryEnergy(), RelicType.SHARED);
       BaseMod.addRelicToCustomPool(new Liver_BoneSpear(), Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_BirdMask(), Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_RelicFruit2(),Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_SleepPotions(),Liver_Color);
       BaseMod.addRelic(new Liver_WhiteSkin(), RelicType.SHARED);
       BaseMod.addRelicToCustomPool(new Liver_GrassJuice(),Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_TalkSign(),Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_Eternal(),Liver_Color);
       BaseMod.addRelicToCustomPool(new Liver_MoonSkin(),Liver_Color);
         //BaseMod.addRelicToCustomPool(new RedCat_RedCatStart(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_CorruptCatalyst(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_Pearl(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_Yoyo(),RedCat_Color);
         BaseMod.addRelicToCustomPool(new RedCat_Menu(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_Menu2(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_Key(),RedCat_Color);
       BaseMod.addRelicToCustomPool(new RedCat_mushroom(),RedCat_Color);


}
   public void receiveEditStrings() {
     String lang;
/* 206 */     if (Settings.language == Settings.GameLanguage.ZHS) {
/* 207 */       lang = "ZHS";
     } else {
/* 209 */       lang = "ENG";
     }
     BaseMod.loadCustomStringsFile(CardStrings.class,"RainworldMod/localization/"+lang+"/card.json");
    BaseMod.loadCustomStringsFile(CharacterStrings.class, "RainworldMod/localization/"+lang+"/character.json");
    BaseMod.loadCustomStringsFile(RelicStrings.class, "RainworldMod/localization/"+lang+"/relics.json");
    BaseMod.loadCustomStringsFile(PowerStrings.class, "RainworldMod/localization/"+lang+"/powers.json");
    BaseMod.loadCustomStringsFile(MonsterStrings.class, "RainworldMod/localization/"+lang+"/monsters.json");
     BaseMod.loadCustomStringsFile(PotionStrings.class,"RainworldMod/localization/"+lang+"/potions.json");
       BaseMod.loadCustomStringsFile(UIStrings.class,"RainworldMod/localization/"+lang+"/UIs.json");
       BaseMod.loadCustomStringsFile(EventStrings.class,"RainworldMod/localization/"+lang+"/Events.json");
       BaseMod.loadCustomStringsFile(AchievementStrings.class,"RainworldMod/localization/"+lang+"/achievements.json");



       }



     @Override
     public void receiveEditKeywords() {
         String lang;
            if (Settings.language == Settings.GameLanguage.ZHS) {
                lang = "ZHS";
         } else {
                lang = "ENG";
         }

         String json = Gdx.files.internal("RainworldMod/localization/"+lang+"/Keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
         Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
         if (keywords != null) {
             for (Keyword keyword : keywords)
                 {
                       BaseMod.addKeyword("rainworld", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
                   }
               }
     }

     @Override
     public void receiveAddAudio() {
           BaseMod.addAudio("MEOW", "sound/MeowNormal8.ogg");
         BaseMod.addAudio("Liver:WorkError", "sound/WorkError.ogg");
         BaseMod.addAudio("Liver:WorkErrorUp", "sound/WorkLevelUp.ogg");
         BaseMod.addAudio("Liver:WorkErrorDown", "sound/WorkLevelDown.ogg");
         BaseMod.addAudio("Liver:FoodUp", "sound/FoodUp.ogg");
         BaseMod.addAudio("Liver:SpearBomb", "sound/BombSpear.ogg");
         BaseMod.addAudio("Liver:Bomb", "sound/Bomb.ogg");
         BaseMod.addAudio("Liver:HoneyComb", "sound/honeycomb.ogg");
         BaseMod.addAudio("Liver:Spear", "sound/Spear.ogg");
         BaseMod.addAudio("Liver:Wugong", "sound/WugongBlock.ogg");
         BaseMod.addAudio("Liver:Strike", "sound/Strike.ogg");
         BaseMod.addAudio("Liver:ThreeAgree", "sound/ThreeAgree.ogg");
         BaseMod.addAudio("Liver:LoveGold", "sound/loveGold.ogg");
         BaseMod.addAudio("Liver:TearWound", "sound/TearWound.ogg");
         BaseMod.addAudio("Liver:PointBoom", "sound/PointBoom.ogg");
         BaseMod.addAudio("Liver:SpearElec", "sound/SpearElec.ogg");
         BaseMod.addAudio("Liver:MushRoom", "sound/MushroomRoom.ogg");
         BaseMod.addAudio("Liver:XiyiBuff", "sound/XiyiBuff.ogg");
         BaseMod.addAudio("Liver:xiyidie", "sound/xiyidie.ogg");
         BaseMod.addAudio("Liver:xiyi1", "sound/xiyi1.ogg");
         BaseMod.addAudio("Liver:xiyi2", "sound/xiyi2.ogg");
         BaseMod.addAudio("Liver:PaoCao", "sound/PaoCao.ogg");
         BaseMod.addAudio("Liver:EndBGM", "sound/EndBGM.ogg");
         BaseMod.addAudio("Liver:WAAAAAA", "sound/WAAAAAA.ogg");
         BaseMod.addAudio("Liver:random1", "sound/random1.ogg");
         BaseMod.addAudio("Liver:random2", "sound/random2.ogg");
         BaseMod.addAudio("Liver:GreenDie", "sound/GreenDie.ogg");
         BaseMod.addAudio("Liver:GreenWenhao", "sound/GreenWenhao.ogg");
         BaseMod.addAudio("RedCat:Eat", "sound/eat.ogg");
         BaseMod.addAudio("Rainworld:Achievements", "sound/Achievements.ogg");


         //CardCrawlGame.sound.play("Liver:GreenWenhao");
     }
    public void receiveAddMonster(){
        //BaseMod.addMonster("Liver:Cabrite", "Cabrites", () -> new Cabrite(0.0F, 0.0F));
        BaseMod.addMonster("Liver:RedCabrite", "RedCabrites", () ->  new MonsterGroup(new AbstractMonster[]{new RedCabrite(-200.0F, 0.0F)}));
        BaseMod.addMonster("Liver:IronBird", "IronBird", () -> new IronBird(0.0F, 0.0F));
        BaseMod.addMonster("Liver:Double", "Double", () ->  new MonsterGroup(new AbstractMonster[]{new RedCabrite(-200.0F, 0.0F),new IronBird(100F,0f)}));
        BaseMod.addMonster("Liver:MushRoom", "MushRoom", () -> new MushRoom2(-200.0F, 0.0F));
        BaseMod.addMonster("Liver:Double", "Double", () ->  new MonsterGroup(new AbstractMonster[]{new RedCabrite(-200.0F, 0.0F),new IronBird(100F,0f)}));
        BaseMod.addMonster("Liver:Hide", "Hide", () ->  new MonsterGroup(new AbstractMonster[]{new MushRoom(-400.0F, 0.0F),new MushRoom(0F,0f)}));
        BaseMod.addMonster("Liver:GreenCabrite", "GreenCabrites", () -> new MonsterGroup(new AbstractMonster[]{new GreenCabrite(0.0F, 0.0F)}));

        BaseMod.addMonster("Rainworld:Fires", "Fires", () ->  new MonsterGroup(new AbstractMonster[]{new RedCabrite(-300.0F, 0.0F),new IronBird(00F,100f),new FireCent(200F,-50f)}));

        BaseMod.addMonster("Rainworld:FireCent", "FireCent", () -> new  FireCent(0.0F, 0.0F));
        BaseMod.addMonster("Rainworld:Watcher", "Watcher", () -> new ThreeChicken(0.0F, 0.0F));

        BaseMod.addStrongMonsterEncounter("Exordium", new MonsterInfo("Liver:GreenCabrite", 2.0F));
        BaseMod.addEliteEncounter("TheCity", new MonsterInfo("Liver:RedCabrite", 1.0F));
        BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("Liver:IronBird", 1.0F));
        BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("Rainworld:FireCent", 1.0F));
       // BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("Liver:Double", 2.0F));
        //BaseMod.addEliteEncounter("TheBeyond", new MonsterInfo("Liver:MushRoom", 1.0F));
        BaseMod.addBoss("TheBeyond","Liver:MushRoom","images/CharacterImg/Monster/MushRoom/Map.png","images/CharacterImg/Monster/MushRoom/Map.png");
    }
     @Override
     public void receivePostUpdate() {
         // 传入游戏原生deltaTime（秒）
         AchievementMgr.achievementPopUpPanel.update(Gdx.graphics.getDeltaTime());
     }

     // 游戏渲染时调用：绘制所有活跃弹窗
     @Override
     public void receiveRender(SpriteBatch sb) {
         AchievementMgr.achievementPopUpPanel.render(sb);
     }

     @Override
     public void receivePostInitialize() {
         initializeEvents();
         ModConfig.initModConfigMenu();
         if(ModConfig.specialEnemyEnable)
            receiveAddMonster();
         receiveEditPotions();
     }



 }

