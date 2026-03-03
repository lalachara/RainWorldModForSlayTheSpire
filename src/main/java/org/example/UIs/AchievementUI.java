package org.example.UIs;

import basemod.ClickableUIElement;
import basemod.CustomCharacterSelectScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.example.Achievement.AchievementMgr;
import org.example.Achievement.AchievementPopUpPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import org.example.Achievement.AchievementSlot;

import java.util.Arrays;
import java.util.List;

public class AchievementUI extends ClickableUIElement {
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("RainWorld:AchievementText");
    private float size = 100;
    private static final Texture icon,xicon1,xicon2;
    private static final List<Texture> xicons;

    public static AchievementUI instance;
    public static final float PANEL_WIDTH = 1600F * Settings.scale;
    public static final float PANEL_HEIGHT = 800.0F * Settings.scale;
    public static final float PANEL_X = (Settings.WIDTH - PANEL_WIDTH) / 2.0F+30F * Settings.scale;
    public static final float PANEL_Y = (Settings.HEIGHT - PANEL_HEIGHT) / 2.0F+130.0F * Settings.scale;
    private static final Texture background1,background2,background3,background4;
    public static boolean shown = false;

    private static final String ACHIEVEMENT_BGM_KEY = "Rainworld:Achievements"; // BGM唯一标识
    private static long soundId; // 播放的BGM ID
    // ===== 翻页核心变量（替代原有滚动变量）=====
    private static final Texture leftBtnTexture1,leftBtnTexture2;  // 左翻页按钮纹理
    private static final Texture rightBtnTexture1,rightBtnTexture2; // 右翻页按钮纹理
    private static final List<Texture> leftBtnTextures,rightBtnTextures;  // 左翻页按钮的不同状态纹理列表
    private ClickableUIElement leftBtn;           // 左翻页按钮UI
    private ClickableUIElement rightBtn;          // 右翻页按钮UI
    private int currentPage = 0;                  // 当前页码（从0开始）
    private static final int SLOTS_PER_PAGE = 32; // 每页显示的成就槽数量（你原代码的PageMaxIcon）
    private static final float SLOT_SPACINGX = 50F * Settings.scale; // 成就槽横向间距
    private static final float SLOT_SPACINGY = 30F * Settings.scale; // 成就槽纵向间距
    private static final float PANEL_PADDINGX = 250F * Settings.scale; // 面板内边距X
    private static final float PANEL_PADDINGY = 270F * Settings.scale;  // 面板内边距Y
    private static final int COLUMNS = 8;         // 每行显示8个成就槽
    private int totalPages = 0;                   // 总页数
    private int show = 0;
    public AchievementUI() {
        super(icon,400,750,110,110);
        setClickable(true);

        // 初始化翻页按钮
        initPageButtons();
    }

    // 初始化翻页按钮
    private void initPageButtons() {
        // 左翻页按钮位置（面板左下角）
        leftBtn = new ClickableUIElement(leftBtnTexture1,
                Settings.WIDTH*0.13F/Settings.scale,
                Settings.HEIGHT*0.25F/Settings.scale,
                150 , 135) {
            @Override
            protected void onClick() {
                prevPage(); // 点击左按钮翻上一页
            }

            @Override
            protected void onHover() {
                // 悬停提示
                TipHelper.renderGenericTip(this.x + 40 * Settings.scale,
                        this.y + 60 * Settings.scale,
                        UIs.TEXT[4], UIs.TEXT[5]);
            }


            @Override
            public void render(SpriteBatch sb) {
                if(hitbox.hovered)
                    sb.draw(leftBtnTextures.get(show),Settings.WIDTH*0.13F-7*Settings.scale, Settings.HEIGHT*0.25F-7*Settings.scale, 165 * Settings.scale, 150 * Settings.scale);
                else
                    sb.draw(leftBtnTextures.get(show),Settings.WIDTH*0.13F, Settings.HEIGHT*0.25F, 150 * Settings.scale, 135 * Settings.scale);
            }

            @Override
            protected void onUnhover() {

            }
        };
        leftBtn.setClickable(true);

        // 右翻页按钮位置（面板右下角）
        rightBtn = new ClickableUIElement(rightBtnTexture1,
                Settings.WIDTH*0.83F/Settings.scale,
                Settings.HEIGHT*0.25F/Settings.scale,
                150, 135 ) {
            @Override
            protected void onClick() {
                nextPage(); // 点击右按钮翻下一页
            }

            @Override
            protected void onHover() {
                // 悬停提示
                TipHelper.renderGenericTip(this.x - 80 * Settings.scale,
                        this.y + 60 * Settings.scale,
                        UIs.TEXT[6], UIs.TEXT[7]);
            }

            @Override
            public void render(SpriteBatch sb) {
                if(hitbox.hovered)
                    sb.draw(rightBtnTextures.get(show),Settings.WIDTH*0.83F-7*Settings.scale, Settings.HEIGHT*0.25F-7*Settings.scale,150 * 1.1F * Settings.scale, 135 * 1.1F * Settings.scale);
                else
                    sb.draw(rightBtnTextures.get(show),Settings.WIDTH*0.83F,Settings.HEIGHT*0.25F,150 * Settings.scale, 135 * Settings.scale);
            }

            @Override
            protected void onUnhover() {

            }
        };
        rightBtn.setClickable(true);
    }

    // 上一页
    private void prevPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    // 下一页
    private void nextPage() {
        calculateTotalPages(); // 实时计算总页数
        if (currentPage < totalPages - 1) {
            currentPage++;
        }
    }

    // 计算总页数
    private void calculateTotalPages() {
        if (AchievementMgr.achievementSlots.isEmpty()) {
            totalPages = 0;
        } else {
            // 总页数 = 总成就数 / 每页显示数，向上取整
            totalPages = (int) Math.ceil((float) AchievementMgr.achievementSlots.size() / SLOTS_PER_PAGE);
        }
    }

    // 原有悬停/离开/点击方法不变
    @Override
    protected void onHover() {
        if(!shown)
            TipHelper.renderGenericTip(
                    super.x - 100F * Settings.scale,
                    super.y + 100F * Settings.scale,
                    UIs.TEXT[2],
                    UIs.TEXT[3]
            );

    }
    @Override
    protected void onUnhover() {
        this.size=100;
    }
    @Override
    protected void onClick() {
        toggle(); // 直接调用本类toggle
    }

    // 更新方法：移除滚动逻辑，新增翻页按钮更新
    @Override
    public void update() {
        super.update();
        if (shown) {
            // 更新翻页按钮状态
            leftBtn.update();
            rightBtn.update();

            // 遍历更新当前页成就槽的悬停状态
            calculateTotalPages();
            int startIndex = currentPage * SLOTS_PER_PAGE;
            int endIndex = Math.min(startIndex + SLOTS_PER_PAGE, AchievementMgr.achievementSlots.size());
            for (int i = startIndex; i < endIndex; i++) {
                AchievementSlot slot = AchievementMgr.achievementSlots.get(i);
                slot.update();
            }
        }
    }

    // 重写渲染方法：替换滚动为翻页
    @Override
    public void render(SpriteBatch sb){
        Color temp = sb.getColor();
        sb.setColor(Color.WHITE);

        // 1. 绘制成就面板背景（仅shown=true时显示）
        if (shown) {
            // 绘制背景
            if(AchievementMgr.achievementUnlocked<0.3*AchievementMgr.achievementAmount){
                sb.draw(background1, PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
                show = 0;
            }

            else if(AchievementMgr.achievementUnlocked<0.6*AchievementMgr.achievementAmount){
                sb.draw(background2, PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
                show = 0;
            }

            else if(AchievementMgr.achievementUnlocked<AchievementMgr.achievementAmount){
                sb.draw(background3, PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
                show = 1;
            }

            else if(AchievementMgr.achievementUnlocked==AchievementMgr.achievementAmount){
                sb.draw(background4, PANEL_X, PANEL_Y, PANEL_WIDTH, PANEL_HEIGHT);
                show = 1;

            }

            // 2. 渲染翻页按钮
            leftBtn.render(sb);
            rightBtn.render(sb);

            // 3. 渲染当前页的成就槽
            renderCurrentPageSlots(sb);

            // 4. 渲染关闭按钮和成就计数
            if(this.hitbox.hovered)
                sb.draw(xicons.get(show), Settings.WIDTH*0.85F-5*Settings.scale, Settings.HEIGHT*0.84F-5*Settings.scale, size*1.1F * Settings.scale, size*1.1F * Settings.scale);
            else
                sb.draw(xicons.get(show), Settings.WIDTH*0.85F, Settings.HEIGHT*0.84F, size * Settings.scale, size * Settings.scale);

            this.hitbox.update(Settings.WIDTH*0.85F, Settings.HEIGHT*0.84F);

            // 渲染成就计数和页码
            String pageText = UIs.TEXT[0]+":"+AchievementMgr.achievementUnlocked+"/"+AchievementMgr.achievementAmount ;
            FontHelper.renderFont(sb, FontHelper.tipHeaderFont, pageText,
                    Settings.WIDTH*0.75F, Settings.HEIGHT*0.87F, Color.WHITE);
            FontHelper.renderFont(sb, FontHelper.tipHeaderFont,  UIs.TEXT[8] + (currentPage + 1) + "/" + (totalPages > 0 ? totalPages : 1)+UIs.TEXT[9],
                    Settings.WIDTH*0.5F, Settings.HEIGHT*0.35F, Color.WHITE);
        }else {
            // 未显示面板时绘制成就图标
            sb.draw(icon, Settings.WIDTH*0.21F, Settings.HEIGHT*0.625F, size * Settings.scale, size * Settings.scale);
            this.hitbox.update(Settings.WIDTH*0.21F, Settings.HEIGHT*0.625F);
        }

        sb.setColor(temp);
    }

    // 渲染当前页的成就槽
    private void renderCurrentPageSlots(SpriteBatch sb) {
        calculateTotalPages();
        float slotSize = AchievementSlot.getSlotSize();
        float slotTotalWidth = slotSize + SLOT_SPACINGX;
        float slotTotalHeight = slotSize + SLOT_SPACINGY;

        // 计算当前页的起始和结束索引
        int startIndex = currentPage * SLOTS_PER_PAGE;
        int endIndex = Math.min(startIndex + SLOTS_PER_PAGE, AchievementMgr.achievementSlots.size());

        // 遍历当前页的成就槽
        for (int i = startIndex; i < endIndex; i++) {
            AchievementSlot slot = AchievementMgr.achievementSlots.get(i);
            // 计算在当前页内的相对索引（从0开始）
            int relativeIndex = i - startIndex;
            // 计算行列
            int col = relativeIndex % COLUMNS;
            int row = relativeIndex / COLUMNS;

            // 计算坐标（固定位置，无滚动）
            slot.x = PANEL_X + PANEL_PADDINGX + col * slotTotalWidth;
            slot.y = PANEL_Y + PANEL_HEIGHT - PANEL_PADDINGY - row * slotTotalHeight;

            // 渲染成就槽
            slot.render(sb);
        }
    }

    // 原有toggle方法不变
    // ===== 修改toggle方法：联动BGM播放/停止 =====
    public static void toggle() {
        shown = !shown;
        if(shown){
            playAchievementBGM();
            CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.hide();
            CardCrawlGame.mainMenuScreen.charSelectScreen.cancelButton.hide();;
        }
        else
        {
            CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.show();
            CardCrawlGame.mainMenuScreen.charSelectScreen.cancelButton.show(CharacterSelectScreen.TEXT[5]);
            stopAchievementBGM();
        }


    }

    // ===== 新增BGM核心工具方法 =====


    public static void playAchievementBGM() {
        SoundMaster soundMaster = CardCrawlGame.sound;
        CardCrawlGame.music.silenceBGM();
        soundId =  soundMaster.playAndLoop(ACHIEVEMENT_BGM_KEY);

    }

    /**
     * 停止成就界面BGM：恢复原BGM播放
     */
    public static void stopAchievementBGM() {
        SoundMaster soundMaster = CardCrawlGame.sound;
        soundMaster.fadeOut(ACHIEVEMENT_BGM_KEY, soundId);
        CardCrawlGame.music.unsilenceBGM();

    }

    // 静态代码块：加载纹理（新增翻页按钮纹理）
    static {
        icon = ImageMaster.loadImage("images/CharacterImg/RainWorld/Achievement.png");
        background1 = ImageMaster.loadImage("images/CharacterImg/chara/AchievementBg1.png");
        background2 = ImageMaster.loadImage("images/CharacterImg/chara/AchievementBg2.png");
        background3 = ImageMaster.loadImage("images/CharacterImg/chara/AchievementBg3.png");
        background4 = ImageMaster.loadImage("images/CharacterImg/chara/AchievementBg4.png");
        xicon1 = ImageMaster.loadImage("images/CharacterImg/Achievements/X1.png");
        xicon2 = ImageMaster.loadImage("images/CharacterImg/Achievements/X2.png");
        xicons = Arrays.asList(
                xicon1,  // 第一个图片
                xicon2  // 第二个图片
        );

        // 加载翻页按钮纹理（请替换为你的实际路径）
        leftBtnTexture1 = ImageMaster.loadImage("images/CharacterImg/Achievements/LeftBtn1.png");
        rightBtnTexture1 = ImageMaster.loadImage("images/CharacterImg/Achievements/RightBtn1.png");
        leftBtnTexture2 = ImageMaster.loadImage("images/CharacterImg/Achievements/LeftBtn2.png");
        rightBtnTexture2 = ImageMaster.loadImage("images/CharacterImg/Achievements/RightBtn2.png");
        leftBtnTextures = Arrays.asList(leftBtnTexture1, leftBtnTexture2);
        rightBtnTextures = Arrays.asList(rightBtnTexture1, rightBtnTexture2);
        instance = new AchievementUI();
    }
}