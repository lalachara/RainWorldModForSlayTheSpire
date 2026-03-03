package org.example.Achievement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.commons.lang3.ObjectUtils;

public class AchievementSlot {
    private final int id;
    private final String name;
    private final String desc;
    //private int unlockedStatus;
    Texture icon ;

    private Texture lockedIcon ;
    private static Texture shadowIcon = ImageMaster.loadImage("images/CharacterImg/Achievements/Shadow.png");
    AchievementStrings achievementStrings = CardCrawlGame.languagePack.getAchievementString("Rainworld:Achievements");
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("RainWorld:AchievementText");
    // ===== 新增：绘制相关核心变量 =====
    public float x, y; // 成就槽的绘制坐标（由AchievementUI统一计算赋值）
    private final Hitbox hb; // 碰撞盒（检测鼠标悬停/点击，适配100*100像素）
    private static float SLOT_SIZE = 100F * Settings.scale,Shadow_Size = 120F*Settings.scale; // 成就槽实际尺寸（100*100适配缩放）
    public boolean hovered,clicked; // 悬停状态标识

    // 构造方法：初始化所有基础属性，默认未解锁 + 初始化碰撞盒
    public AchievementSlot(int id) {
        this.id = id;
        this.name = achievementStrings.NAMES[id];
        this.desc = achievementStrings.TEXT[id];
        //this.unlockedStatus = AchievementMgr.getAchievementStatus(id);

        icon = ImageMaster.loadImage("images/CharacterImg/Achievements/" + id + ".png");
        lockedIcon = ImageMaster.loadImage("images/CharacterImg/Achievements/" + id + "Locked.png");

        if(icon== null)
            icon = ImageMaster.loadImage("images/CharacterImg/Achievements/1.png");

        if(lockedIcon==null)
            lockedIcon = ImageMaster.loadImage("images/CharacterImg/Achievements/1Locked.png");


        // ===== 新增：初始化碰撞盒（尺寸与成就槽一致）=====
        this.hb = new Hitbox(SLOT_SIZE, SLOT_SIZE);

        this.hovered = false;
    }

    // 原有解锁方法不变


    // ===== 新增：核心update方法（更新碰撞盒+检测鼠标悬停）=====
    public void update() {
        // 更新碰撞盒位置（与成就槽绘制坐标对齐，居中）
        hb.move(x + SLOT_SIZE / 2F, y + SLOT_SIZE / 2F);
        this.hb.clickStarted = true;
        hb.update();
        // 更新悬停状态
        this.hovered = hb.hovered;
        this.clicked = hb.clicked;
        // 悬停时渲染成就提示（名称+描述），贴合原生Tip样式
        if (hovered) {
            TipHelper.renderGenericTip(
                    x + SLOT_SIZE + 20F * Settings.scale, // 提示框在成就槽右侧
                    y + SLOT_SIZE / 2F,
                    this.name, // 成就名称
                    this.desc+" NL "+ ((AchievementMgr.achievementStatusMap.get(id) != 0)?" #g"+UIs.TEXT[0]+" ":" #r"+UIs.TEXT[1]+" ")// 成就描述

            );
//            SLOT_SIZE = 110*Settings.scale;
//            Shadow_Size = 130*Settings.scale;

        }
        if(clicked){
            //AchievementMgr.lock(id);
            hb.clicked = false;
        }
    }


    // ===== 新增：核心render方法（绘制成就槽图标+解锁状态遮罩）=====
    public void render(SpriteBatch sb) {
        if(hb.hovered)
            sb.draw(shadowIcon,x-(Shadow_Size-SLOT_SIZE)/2-10*Settings.scale,y-(Shadow_Size-SLOT_SIZE)/2-10*Settings.scale,Shadow_Size+20*Settings.scale,Shadow_Size+20*Settings.scale);
        else
            sb.draw(shadowIcon,x-(Shadow_Size-SLOT_SIZE)/2,y-(Shadow_Size-SLOT_SIZE)/2,Shadow_Size,Shadow_Size);
        // 2. 未解锁成就添加半透明黑色遮罩（视觉区分，解锁状态=0为未解锁，可根据你的规则调整）
        if (AchievementMgr.achievementStatusMap.get(id) == 0) {
            if(hb.hovered)
                sb.draw(lockedIcon, x-(Shadow_Size-SLOT_SIZE)/2, y-(Shadow_Size-SLOT_SIZE)/2, Shadow_Size, Shadow_Size);
            else
                sb.draw(lockedIcon, x, y, SLOT_SIZE, SLOT_SIZE);
        }else
            if(hb.hovered)
                sb.draw(icon, x-(Shadow_Size-SLOT_SIZE)/2, y-(Shadow_Size-SLOT_SIZE)/2, Shadow_Size, Shadow_Size);
            else
                sb.draw(icon, x, y, SLOT_SIZE, SLOT_SIZE);

        //System.out.println("AchievementSlot Rendered: ID=" + id + ", UnlockedStatus=" + unlockedStatus);

        // 3. 调试用：绘制碰撞盒（发布时可注释）
        // hb.render(sb);

    }

    // 原有getter方法不变
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    //public int getUnlockedStatus() { return unlockedStatus; }
    // ===== 新增：获取成就槽实际尺寸（给AchievementUI布局用）=====
    public static float getSlotSize() {
        return SLOT_SIZE;
    }
}