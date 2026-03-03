package org.example.UIs;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class SleepUIEffect {
    // 参考原版卡牌发光系统
    private final Hitbox element;
    private boolean isGlowing = true;
    private float glowTimer = 0.0f;
    private TextureRegion glowTexture;
    private final ArrayList<UIGlowBorder> glowList = new ArrayList<>();
    private Color glowColor ;

    public SleepUIEffect(Hitbox element, TextureRegion img, Color color) {
        this.element = element;
        this.glowTexture = img;
        this.glowColor = color;
    }

    public void update() {
        if (this.isGlowing) {
            this.glowTimer -= Gdx.graphics.getDeltaTime();
            if (this.glowTimer < 0.0f) {
                this.glowList.add(new UIGlowBorder(this.element, this.glowTexture,this.glowColor));
                this.glowTimer = 0.7f; // 与原版相同的间隔
            }
        }

        Iterator<UIGlowBorder> iterator = this.glowList.iterator();
        while (iterator.hasNext()) {
            UIGlowBorder effect = iterator.next();
            effect.update();
            if (effect.isDone) {
                iterator.remove();
            }
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.glowList.isEmpty()) {
            // 保存当前混合模式
            int srcFunc = sb.getBlendSrcFunc();
            int dstFunc = sb.getBlendDstFunc();

            // 设置发光混合模式
            sb.setBlendFunction(770, 1);

            // 渲染所有发光效果
            for (UIGlowBorder effect : this.glowList) {
                effect.render(sb);
            }

            // 关键修复：始终恢复为默认混合模式
            sb.setBlendFunction(srcFunc, dstFunc);
        }
    }
}

class UIGlowBorder extends AbstractGameEffect {
    // 完全模仿原版CardGlowBorder的参数
    private static final float DURATION = 1.3f;
    private static final float MAX_SCALE = 1.21f;

    private final Hitbox element;
    private final TextureRegion glowTexture;
    private float scale;

    public UIGlowBorder(Hitbox element,TextureRegion glowTexture ,Color glowColor) {
        this.element = element;

        // 使用与原版相同的纹理 (技能卡发光轮廓)
        this.glowTexture = glowTexture;

        // 设置效果属性
        this.duration = DURATION;
        this.startingDuration = DURATION;

        // 设置颜色 (添加透明度)
        this.color = glowColor.cpy();
        this.color.a = 0.7f;

        // 初始缩放
        this.scale = 1.0f;

    }

    @Override
    public void update() {
        // 更新持续时间
        this.duration -= Gdx.graphics.getDeltaTime();

        // 计算进度 (0-1)
        float progress = 1.0f - (duration / startingDuration);

        // 使用与原版相同的插值函数
        this.scale = 1.0f + Interpolation.pow2Out.apply(0.03f, MAX_SCALE - 1.0f, progress);

        // 更新透明度
        this.color.a = this.duration / 1.5f;

        // 检查是否完成
        if (this.duration < 0.0f) {
            this.isDone = true;
            this.duration = 0.0f;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // 计算位置和尺寸
        float width = element.width * this.scale;
        float height = element.height * this.scale ;
        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;

        // 设置颜色
        sb.setColor(this.color);

        // 绘制发光边框 (完全模仿原版绘制逻辑)
        sb.draw(
                this.glowTexture,
                element.cX - halfWidth,
                element.cY - halfHeight,
                halfWidth, halfHeight,
                width, height,
                1.0f, 1.0f,
                0.0f
        );
    }

    @Override
    public void dispose() {
        // 不需要手动处理纹理
    }
}