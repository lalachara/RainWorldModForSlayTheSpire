package org.example.Achievement;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import org.example.tools.ModConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AchievementMgr  {
    public static Map<Integer, Integer> achievementStatusMap = new java.util.HashMap<>();
    public static List<AchievementSlot> achievementSlots = new ArrayList<>();
    public static final AchievementPopUpPanel achievementPopUpPanel = new AchievementPopUpPanel();
    public static int achievementAmount=0,achievementUnlocked=0;

    public static void init(String achievementsString) {

        if(!achievementStatusMap.isEmpty())
            return;

        int achievementListSize = GetAchievementStrings().NAMES.length;
        achievementStatusMap.clear();

        try {
            // 校验1：字符串为null/空，直接触发全量重置（所有状态设0）
            if (achievementsString == null || achievementsString.isEmpty()) {
                resetAllStatusToZero();
                return;
            }
            // 步骤2：按逗号切片，得到单个成就的键值对字符串（如["0:0", "1:2"]）
            String[] kvPairs = achievementsString.split(",");
            // 校验2：切片后长度 > 成就列表长度，触发全量重置
            if (kvPairs.length > achievementListSize) {
                resetAllStatusToZero();
                return;
            }

            // 步骤3：遍历切片，解析每个ID和Status，存入外部Map
            for (String kv : kvPairs) {
                // 按冒号分割键值，必须严格分割为2部分，否则格式错误
                String[] idAndStatus = kv.split(":");
                if (idAndStatus.length != 2) {
                    throw new IllegalArgumentException("键值对格式错误，必须为int:int");
                }

                // 解析为int类型（非数字会触发NumberFormatException，进入catch）
                int achievementId = Integer.parseInt(idAndStatus[0].trim());
                int achievementStatus = Integer.parseInt(idAndStatus[1].trim());
                // 存入外部Map（自动覆盖重复ID，保证唯一性）
                achievementStatusMap.put(achievementId, achievementStatus);
            }

            // 步骤4：自动补全后续ID：若切片长度 < 成就列表长度，按次序补全剩余ID，状态设0
            completeSubsequentAchievements(kvPairs.length);


        } catch (Exception e) {
            // 捕获所有异常：格式错误、转int失败、分割异常等，统一触发全量重置
            // 异常类型包含：NumberFormatException、IllegalArgumentException、ArrayIndexOutOfBoundsException等
            resetAllStatusToZero();
        }
        initAchievementSlots();
    }
    private static void updateAmount(){
        achievementAmount = achievementSlots.size();
        int temp = 0;
        for (int i = 0; i < achievementSlots.size(); i++) {
            if(achievementStatusMap.get(i)!=0)
                temp++;
        }
        achievementUnlocked = temp;
    }
    private static void initAchievementSlots(){

        List<AchievementSlot> temp = new ArrayList<>();
        for (int i = 0; i < GetAchievementStrings().NAMES.length; i++) {
            temp.add(new AchievementSlot(i));
        }
        achievementSlots = temp;
        updateAmount();

    }

    public static int getAchievementStatus(int achievementId){
        return achievementStatusMap.getOrDefault(achievementId,0);
    }
    private static void resetAllStatusToZero() {
        for (int i = 0; i < GetAchievementStrings().NAMES.length; i++) {
            achievementStatusMap.put(i, 0);
        }
        initAchievementSlots();
    }

    private static void completeSubsequentAchievements(int parsedKvCount) {
        // 若已解析数量 >= 总长度，无需补全
        if (parsedKvCount >= GetAchievementStrings().NAMES.length) {
            return;
        }
        // 从parsedKvCount开始，到achievementListSize-1，按次序补全
        for (int i = parsedKvCount; i < GetAchievementStrings().NAMES.length; i++) {
            // 仅当Map中无该ID时补全（避免覆盖已解析的非连续ID）
            achievementStatusMap.putIfAbsent(i, 0);
        }
    }

    public static String MaptoString() {
        // 1. 按成就ID升序排序，避免乱序导致解析错误
        List<Map.Entry<Integer, Integer>> sortedEntries = achievementStatusMap.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .collect(Collectors.toList());

        // 2. 拼接为"id:status"格式，逗号分隔，空Map返回空字符串
        return sortedEntries.stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(","));
    }

    public static void unlockAchievement(int achievementId) {

        if(achievementStatusMap.get(achievementId) != null){
            int status = AbstractDungeon.ascensionLevel == 20 ? 2 : 1;
            if (achievementStatusMap.get(achievementId) ==0) {
                achievementPopUpPanel.show(achievementId);
                achievementStatusMap.put(achievementId, status);
                ModConfig.SaveAchievements();
                //System.out.println(achievementId+"成就解锁状态：="  +AchievementMgr.achievementStatusMap.get(achievementId));
            }
        }

    }

    public static void lock(int achievementId) {

        if(achievementStatusMap.get(achievementId) != null){
            if (achievementStatusMap.get(achievementId) !=0) {

                achievementStatusMap.put(achievementId, 0);
                ModConfig.SaveAchievements();
                initAchievementSlots();
            }
            else {
                achievementStatusMap.put(achievementId, 1);
                ModConfig.SaveAchievements();
                initAchievementSlots();
            }
        }

    }

    public static  AchievementStrings GetAchievementStrings(){
        return CardCrawlGame.languagePack.getAchievementString("Rainworld:Achievements");
    }

}
