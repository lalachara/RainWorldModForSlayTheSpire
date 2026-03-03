package org.example.UIs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.example.Character.Liver;
import org.example.Character.SlugCat;
import org.example.cards.Liver.others.Liver_stubborn;
import org.example.powers.RedCat.RedMushroomBuff;
import org.example.powers.WorkErrorBuff;
import org.example.powers.WorkLevelLockBuff;
import org.example.tools.Tools;

public class WorkLevelIndicator  {
    // 纹理相关
    private static final String TEXTURE_PATH = "images/CharacterImg/RainWorld/WorkLevels/WorkLevel.png";
    private static final String LOCK_TEXTURE_PATH = "images/CharacterImg/RainWorld/WorkLevels/WorkLevelLock.png";
    private static final String MUSH_TEXTURE_PATH = "images/CharacterImg/RainWorld/WorkLevels/WorkLevelLock2.png";

    private Texture fullTexture;
    private Texture lockTexture;
    private Texture mushTexture;
    private TextureRegion currentRegion;

    private final float X = 94* Settings.scale;
    private final float Y = 340* Settings.scale;
    // 尺寸参数
    private static final int ICON_WIDTH = 130;
    private static final int ICON_HEIGHT = 130;
    private float displayWidth, displayHeight;

    // 位置参数
    private float currentX, currentY;
    private float startY;   // 动画起始Y位置
    private float targetY;  // 动画目标Y位置
    private float currentTextureY; // 当前纹理Y坐标

    // 动画状态
    private boolean isAnimating;
    private float animationDuration = 0.5f;
    private float animationTimer;
    private int lastWorkLevel = 0;
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("Liver:WorkLevel");

    Hitbox hitbox;
    SlugCat player;

    public WorkLevelIndicator(SlugCat player) {
        // 加载纹理
        this.player = player;
        fullTexture = ImageMaster.loadImage(TEXTURE_PATH);
        lockTexture = ImageMaster.loadImage(LOCK_TEXTURE_PATH);
        mushTexture = ImageMaster.loadImage(MUSH_TEXTURE_PATH);

        // 设置显示尺寸
        displayWidth = ICON_WIDTH * Settings.scale;
        displayHeight = ICON_HEIGHT * Settings.scale;

        // 初始位置
        currentX = 84* Settings.scale;
        currentY = 330* Settings.scale;
        float hitboxWidth = 110F* Settings.scale; // 弹药栏宽度
        float hitboxHeight = 110F* Settings.scale; // 弹药栏高度

        hitbox = new Hitbox(X, Y, hitboxWidth, hitboxHeight);
        // 初始纹理位置（底部图标）
        currentTextureY = calculateTextureY(0);
        updateTextureRegion();
    }

    public void update(int workLevel) {
        hitbox.update();
        if (this.hitbox.hovered) {
            this.onHover();
        } else {
            this.onUnhover();
        }

        // 如果worklevel变化，启动动画
        if (workLevel != lastWorkLevel ) {
            // 保存当前纹理位置作为动画起点
            startY = currentTextureY;
            // 计算目标纹理位置
            targetY = calculateTextureY(workLevel);
            isAnimating = true;
            animationTimer = 0;
            lastWorkLevel = workLevel;
        }

        // 处理滚动动画
        if (isAnimating) {
            animationTimer += Gdx.graphics.getDeltaTime();
            float progress = Math.min(animationTimer / animationDuration, 1.0f);

            // 使用缓动函数
            float easedProgress = Interpolation.exp5Out.apply(progress);

            // 插值计算当前纹理位置
            currentTextureY = startY + (targetY - startY) * easedProgress;

            // 更新纹理区域
            updateTextureRegion();

            // 动画结束
            if (progress >= 1.0f) {
                isAnimating = false;
                // 确保精确停在目标位置
                currentTextureY = targetY;
                updateTextureRegion();
            }
        }
    }

    // 计算对应worklevel的纹理Y坐标
    private float calculateTextureY(int workLevel) {
        // 确保worklevel在0-9范围内
        int clampedLevel = Math.min(Math.max(workLevel, 0), 9);
        // 0级在底部 (位置最大)，9级在顶部 (位置0)
        return (9 - clampedLevel) * ICON_HEIGHT;
    }
    public Texture getCurrentTexture() {
        if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(RedMushroomBuff.POWER_ID))
            return mushTexture;
        else if(AbstractDungeon.player!=null&&AbstractDungeon.player.hasPower(WorkLevelLockBuff.POWER_ID))
            return lockTexture;
        else
            return fullTexture;
    }
    // 更新纹理区域
    private void updateTextureRegion() {
        // 创建新的纹理区域
        currentRegion = new TextureRegion(
                getCurrentTexture(),
                0,
                (int)currentTextureY, // 使用计算出的Y坐标
                ICON_WIDTH,
                ICON_HEIGHT
        );
    }

    public void render(SpriteBatch sb) {
        if ( currentRegion == null) return;
        hitbox.move(X+55F* Settings.scale, Y+55F* Settings.scale);
        hitbox.render(sb);
        updateTextureRegion();
        Color temp = sb.getColor();
        if((AbstractDungeon.player.hasPower(WorkErrorBuff.POWER_ID)||AbstractDungeon.player.hasPower(RedMushroomBuff.POWER_ID))&&!isAnimating) {
            sb.setColor(Color.RED);
        }
        // 绘制图标
        sb.draw(
                currentRegion,
                currentX,
                currentY,
                displayWidth,
                displayHeight
        );
        sb.setColor(temp);
    }

    protected void onHover() {
        TipHelper.renderGenericTip(
                InputHelper.mX + 20.0F * Settings.scale,
                InputHelper.mY,
                UIs.TEXT[0],
                UIs.TEXT[(AbstractDungeon.player instanceof Liver?1:3)] + (player.workLevel+1) +"/"+(Tools.hasCard(Liver_stubborn.ID)?player.maxWorkLevel:(player.maxWorkLevel+1)
                ));
    }

    protected void onUnhover() {

    }


    // 清理资源
    public void dispose() {
        if (fullTexture != null) {
            fullTexture.dispose();
            fullTexture = null;
        }
    }
}