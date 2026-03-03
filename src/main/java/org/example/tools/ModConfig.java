package org.example.tools;

import basemod.*;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.example.Achievement.AchievementMgr;

import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    private static final String ENABLED_SPECIAL_ENEMY = "RainWorld:ENABLED_SPECIAL_ENEMY";
    private static final String ENABLED_REALMODE = "RainWorld:ENABLED_REALMODE";
    private static final String REMOVE_HpRelic = "RainWorld:REMOVE_HpRelic";
    private static final String REMOVE_Hp2Relic = "RainWorld:REMOVE_Hp2Relic";
    private static final String ENABLE_SHOPSKIN = "RainWorld:ENABLE_SHOPSKIN";
    private static final String ENABLE_SPECIAL_EVENT = "RainWorld:ENABLE_SPECIAL_EVENT";
    //这里复制一行

    private static Properties defaultSetting = new Properties();
    private static ModPanel settingsPanel;

    private static final float GAP = 40.0F;

    private static final float BASE_X_POSE = 380F;
    private static final float BASE_Y_POSE = 720F;

    public static boolean specialEnemyEnable = true;
    public static boolean realModeEnable = true;
    public static boolean removeHpRelic = true;
    public static boolean removeHp2Relic = true;
    public static boolean enableShopSkin = true;
    public static boolean specialEventEnable = true;
    //这里复制一行

    public static String AchievementsString = null;

    public static SpireConfig config = null;

    public static void initModSettings() {
        defaultSetting.setProperty(ENABLED_SPECIAL_ENEMY, String.valueOf(true));
        defaultSetting.setProperty(ENABLED_REALMODE, String.valueOf(false));
        defaultSetting.setProperty(REMOVE_HpRelic, String.valueOf(true));
        defaultSetting.setProperty(REMOVE_Hp2Relic, String.valueOf(true));
        defaultSetting.setProperty(ENABLE_SHOPSKIN, String.valueOf(true));
        defaultSetting.setProperty(ENABLE_SPECIAL_EVENT, String.valueOf(true));
        defaultSetting.setProperty("Rainworld:AchievementsString", "");
        //这里复制一行



        try {
            config = new SpireConfig("RainWorld", "Common", defaultSetting);
            config.load();
            specialEnemyEnable = config.getBool(ENABLED_SPECIAL_ENEMY);
            realModeEnable = config.getBool(ENABLED_REALMODE);
            AchievementsString = config.getString("Rainworld:AchievementsString");
            removeHpRelic = config.getBool(REMOVE_HpRelic);
            removeHp2Relic = config.getBool(REMOVE_Hp2Relic);
            enableShopSkin = config.getBool(ENABLE_SHOPSKIN);
            specialEventEnable = config.getBool(ENABLE_SPECIAL_EVENT);
            //这里复制一行



        } catch (Exception e) {
            System.out.println("Init Config Failed" + e.getLocalizedMessage());
        }

    }
    public static void initModConfigMenu() {
        settingsPanel = new ModPanel();
        addEnableMenu();
        String modConfDesc = (CardCrawlGame.languagePack.getUIString("RainWorld:EnableText")).TEXT[1];
        Texture badge = ImageMaster.loadImage("images/CharacterImg/RainWorld/ModConfig.png");
        BaseMod.registerModBadge(badge, "RainWorld", "lalacha&characute", modConfDesc, settingsPanel);

    }
    public static void addEnableMenu() {
        UIStrings uis = CardCrawlGame.languagePack.getUIString("RainWorld:EnableText");
        ModLabeledToggleButton btn = new ModLabeledToggleButton(uis.TEXT[0], BASE_X_POSE, BASE_Y_POSE, Settings.CREAM_COLOR, FontHelper.charDescFont, specialEnemyEnable, settingsPanel, modLabel -> {
        },modToggleButton -> {
                specialEnemyEnable = modToggleButton.enabled;
               config.setString("RainWorld:ENABLED_SPECIAL_ENEMY", String.valueOf(specialEnemyEnable));
                 try {
                    config.save();
                    } catch (IOException e) {
                     System.out.println("save config credit failed" + e.getLocalizedMessage());
              }
             });

        ModLabeledToggleButton RMbtn = new ModLabeledToggleButton(uis.TEXT[2], BASE_X_POSE, BASE_Y_POSE-GAP, Settings.CREAM_COLOR, FontHelper.charDescFont, realModeEnable, settingsPanel, modLabel -> {
        },modToggleButton -> {
            realModeEnable = modToggleButton.enabled;
            config.setString("RainWorld:ENABLED_REALMODE", String.valueOf(realModeEnable));
            try {
                config.save();
            } catch (IOException e) {
                System.out.println("save config credit failed" + e.getLocalizedMessage());
            }
        });
        ModLabeledToggleButton HRbtn = new ModLabeledToggleButton(uis.TEXT[3], BASE_X_POSE, BASE_Y_POSE-2*GAP, Settings.CREAM_COLOR, FontHelper.charDescFont, removeHpRelic, settingsPanel, modLabel -> {
        },modToggleButton -> {
            removeHpRelic = modToggleButton.enabled;//改这里
            config.setString("RainWorld:REMOVE_HpRelic", String.valueOf(removeHpRelic));//改这里（2个）
            try {
                config.save();
            } catch (IOException e) {
                System.out.println("save config credit failed" + e.getLocalizedMessage());
            }
        });
        ModLabel modLabel1 = new ModLabel(uis.TEXT[4], BASE_X_POSE+GAP, BASE_Y_POSE-3*GAP+10F, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, l -> {

        });
            ModLabeledToggleButton HR2btn = new ModLabeledToggleButton(uis.TEXT[5], BASE_X_POSE, BASE_Y_POSE-4*GAP, Settings.CREAM_COLOR, FontHelper.charDescFont, removeHp2Relic, settingsPanel, modLabel -> {
        },modToggleButton -> {
            removeHp2Relic = modToggleButton.enabled;//改这里
            config.setString("RainWorld:REMOVE_Hp2Relic", String.valueOf(removeHp2Relic));//改这里（2个）
            try {
                config.save();
            } catch (IOException e) {
                System.out.println("save config credit failed" + e.getLocalizedMessage());
            }
        });
        ModLabel modLabel2 = new ModLabel(uis.TEXT[6], BASE_X_POSE+GAP, BASE_Y_POSE-5*GAP+10F, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, l -> {});

        ModLabeledToggleButton SPSbtn = new ModLabeledToggleButton(uis.TEXT[7], BASE_X_POSE, BASE_Y_POSE-6*GAP, Settings.CREAM_COLOR, FontHelper.charDescFont, enableShopSkin, settingsPanel, modLabel -> {
        },modToggleButton -> {
            enableShopSkin = modToggleButton.enabled;//改这里
            config.setString(ENABLE_SHOPSKIN, String.valueOf(enableShopSkin));//改这里（2个）
            try {
                config.save();
            } catch (IOException e) {
                System.out.println("save config credit failed" + e.getLocalizedMessage());
            }
        });
        ModLabeledToggleButton SPEbtn = new ModLabeledToggleButton(uis.TEXT[8], BASE_X_POSE, BASE_Y_POSE-7*GAP, Settings.CREAM_COLOR, FontHelper.charDescFont, specialEventEnable, settingsPanel, modLabel -> {
        },modToggleButton -> {
            specialEventEnable = modToggleButton.enabled;//改这里
            config.setString(ENABLE_SPECIAL_EVENT, String.valueOf(specialEventEnable));//改这里（2个）
            try {
                config.save();
            } catch (IOException e) {
                System.out.println("save config credit failed" + e.getLocalizedMessage());
            }
        });
//这里复制一段
        settingsPanel.addUIElement((IUIElement)btn);
        settingsPanel.addUIElement((IUIElement)RMbtn);
        settingsPanel.addUIElement((IUIElement)HRbtn);
        settingsPanel.addUIElement((IUIElement)HR2btn);
        settingsPanel.addUIElement((IUIElement)modLabel1);
        settingsPanel.addUIElement((IUIElement)modLabel2);
        settingsPanel.addUIElement((IUIElement)SPSbtn);
        settingsPanel.addUIElement((IUIElement)SPEbtn);

        //这里复制一行

    }

    public static void SaveAchievements() {
        SpireConfig config = ModConfig.config;
        assert config != null;
        String achievementString = AchievementMgr.MaptoString();
        AchievementsString = achievementString;
        config.setString("Rainworld:AchievementsString", achievementString);
        try {
            config.save();
        } catch (IOException e) {
            System.out.println("save config credit failed" + e.getLocalizedMessage());
        }
    }
}
