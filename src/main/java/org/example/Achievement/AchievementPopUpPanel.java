package org.example.Achievement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.red.Feed;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// 完全模仿SlotBgLibrary模板，无多余接口、无多余字段，纯空成就弹窗面板
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 成就达成弹窗：非单例·支持多成就同时弹窗·错位显示·自动消失
public class AchievementPopUpPanel {
    // ===================== 弹窗全局配置（可根据视觉需求调整，统一生效）=====================
    // 单个弹窗基础尺寸（适配缩放）
    public static final float PANEL_WIDTH = 500.0F * Settings.scale;
    public static final float PANEL_HEIGHT = 150.0F * Settings.scale;
    // 弹窗基础位置：屏幕右侧上方（主位置，后续弹窗向下错位），不遮挡游戏核心操作区
    private static final float BASE_PANEL_X = (Settings.WIDTH - PANEL_WIDTH)/2;
    private static final float BASE_PANEL_Y = Settings.HEIGHT - 200.0F * Settings.scale;
    // 多弹窗垂直错位间距（弹窗之间的间隔，避免重叠）
    private static final float POPUP_OFFSET_Y = PANEL_HEIGHT + 20.0F * Settings.scale;
    // 内边距/图标文字间距
    private static final float PADDING = 20.0F * Settings.scale;
    private static final float ICON_TEXT_GAP = 15.0F * Settings.scale;
    // 单个弹窗显示时长（毫秒），所有弹窗统一
    private static final float SHOW_DURATION = 5000.0F;
    // 成就图标尺寸（与AchievementSlot保持100*100一致）
    private static final float ICON_SIZE = 100.0F * Settings.scale;
    // 成就达成提示文字
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("RainWorld:AchievementText");

    private static final String UNLOCK_TEXT = UIs.TEXT[10];

    private static Texture panelBg = ImageMaster.loadImage("images/CharacterImg/Achievements/img.png"); // 弹窗背景纹理（所有弹窗复用）
    private final List<AchievementPopUp> activePopUps; // 存储所有活跃弹窗的列表

    // ===================== 内部类：单个成就弹窗实体（独立计时/位置/成就信息）=====================
    private class AchievementPopUp {
        public AchievementSlot achievement; // 关联的成就槽
        public float x, y; // 该弹窗的绘制坐标
        public float timer; // 该弹窗的独立计时器（毫秒）
        public boolean isValid; // 有效性标识，避免空指针

        // 单个弹窗构造方法：传入成就+坐标，初始化状态
        public AchievementPopUp(AchievementSlot achievement, float x, float y) {
            this.achievement = achievement;
            this.x = x;
            this.y = y;
            this.timer = 0.0F;
            this.isValid = achievement != null;
        }
    }

    // ===================== 外部构造方法（直接实例化使用，非单例）=====================
    public AchievementPopUpPanel() {
        this.activePopUps = new ArrayList<>();
        initGlobalTextures(); // 初始化全局纹理（仅执行一次）
    }

    // ===================== 对外核心调用方法（传入成就ID，创建新弹窗，支持多ID连续调用）=====================

    public void show(int achievementId) {
        // 1. 根据ID查找成就槽，容错校验
        AchievementSlot target = findAchievementById(achievementId);
        if (target == null) {
            return;
        }
        // 2. 计算新弹窗的坐标：根据当前活跃弹窗数量，自动向下错位
        float newX = BASE_PANEL_X;
        float newY = BASE_PANEL_Y - (activePopUps.size() * POPUP_OFFSET_Y);
        // 3. 添加新弹窗到活跃列表
        activePopUps.add(new AchievementPopUp(target, newX, newY));
        // 4. 播放成就达成音效（每个弹窗独立播放，贴合原生体验）
        //CardCrawlGame.sound.play("UNLOCK_ACHIEVEMENT");
    }

    // ===================== 帧更新方法（需在游戏主更新循环中调用，处理计时+自动移除）=====================
    /**
     * 遍历更新所有活跃弹窗，独立计时，超时自动移除
     * @param deltaTime 游戏原生帧间隔时间（秒），保证计时精准
     */
    public void update(float deltaTime) {
        if (activePopUps.isEmpty()) return;

        // 用迭代器遍历，支持遍历时移除元素（避免ConcurrentModificationException）
        Iterator<AchievementPopUp> it = activePopUps.iterator();
        while (it.hasNext()) {
            AchievementPopUp pop = it.next();
            if (!pop.isValid) {
                it.remove();
                continue;
            }
            // 累计当前弹窗的计时器（秒转毫秒）
            pop.timer += deltaTime * 1000.0F;
            // 计时器达到时长，移除该弹窗
            if (pop.timer >= SHOW_DURATION) {
                it.remove();

            }
        }
    }

    // ===================== 渲染方法（需在游戏主渲染循环中调用，绘制所有活跃弹窗）=====================
    /**
     * 遍历渲染所有活跃弹窗，错位显示，无重叠
     */
    public void render(SpriteBatch sb) {
        if (activePopUps.isEmpty()) return;

        Color originalColor = sb.getColor();
        for (AchievementPopUp pop : activePopUps) {
            if (pop.isValid && pop.achievement != null) {
                drawSinglePopUp(sb, pop); // 绘制单个弹窗
            }
        }
        sb.setColor(originalColor); // 恢复原颜色，避免影响其他UI
    }

    // ===================== 私有工具方法（内部逻辑，不对外暴露）=====================

    private void initGlobalTextures() {
        if (panelBg != null) return; // 已初始化，直接返回
        try {
            // 加载自定义弹窗背景（与你现有成就资源路径一致，建议500*150半透明）
            panelBg = ImageMaster.loadImage("images/CharacterImg/Achievements/img.png");
        } catch (Exception e) {
            // 容错：加载失败使用游戏原生白色方块，不影响功能
            panelBg = ImageMaster.WHITE_SQUARE_IMG;
        }
    }


    private AchievementSlot findAchievementById(int achievementId) {
        for (AchievementSlot slot : AchievementMgr.achievementSlots) {
            if (slot.getId() == achievementId) {
                return slot;
            }
        }
        return null;
    }

    /**
     * 绘制单个弹窗：背景+图标+文字，贴合游戏原生风格
     */
    private void drawSinglePopUp(SpriteBatch sb, AchievementPopUp pop) {
        // 1. 绘制弹窗背景（深紫黑半透明，原生风格）
        sb.setColor(Color.WHITE);
        sb.draw(panelBg, pop.x, pop.y, PANEL_WIDTH, PANEL_HEIGHT);
        // 2. 绘制成就图标（左对齐，垂直居中）
        float iconX = pop.x + PADDING;
        float iconY = pop.y + (PANEL_HEIGHT - ICON_SIZE) / 2.0F;
        sb.draw(pop.achievement.icon, iconX, iconY, ICON_SIZE, ICON_SIZE);

        // 3. 绘制成就文字：金色提示+白色名称+浅灰描述
        float textStartX = iconX + ICON_SIZE + ICON_TEXT_GAP;
        float lineHeight = 30.0F * Settings.scale;
        // 3.1 金色“成就达成！”提示
        float unlockY = pop.y + PANEL_HEIGHT - PADDING - 5.0F * Settings.scale;
        FontHelper.renderFont(sb, FontHelper.tipHeaderFont, UNLOCK_TEXT,
                textStartX, unlockY, Color.GOLD);
        // 3.2 白色成就名称
        float nameY = unlockY - lineHeight;
        FontHelper.renderFont(sb, FontHelper.tipHeaderFont, pop.achievement.getName(),
                textStartX, nameY, Color.WHITE);
        // 3.3 浅灰色成就描述
        float descY = nameY - lineHeight;
        FontHelper.renderFont(sb, FontHelper.tipBodyFont, pop.achievement.getDesc(),
                textStartX, descY, new Color(0.8F, 0.8F, 0.8F, 1F));
    }

    // ===================== 可选公共方法（外部调用，强制清理/判断）=====================
    /**
     * 强制移除所有活跃弹窗（如游戏退出/暂停时调用，避免残留）
     */
    public void clearAll() {
        activePopUps.clear();
    }

    /**
     * 判断当前是否有活跃的成就弹窗
     */
    public boolean hasActivePopUps() {
        return !activePopUps.isEmpty();
    }
}