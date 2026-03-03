package org.example.UIs;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.example.Character.RedCat;
import org.example.powers.RedCat.RedMushroomBuff;

public class RedCatUI extends ClickableUIElement {
    private static final Texture ByKuang = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar_K.png");
    private static final Texture ByTiao = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar.png");
    private static final Texture ByTiao1 = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar1.png");
    private static final Texture ByTiao2 = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar2.png");
    private static final Texture ByTiao3 = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar3.png");
    private static final Texture ByTiao4 = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar4.png");

    private static final Texture ByKuangbtm = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar_Kbtm.png");
    private static final Texture ByTiaobtm = ImageMaster.loadImage("images/CharacterImg/RainWorld/RedCatVar_btm.png");
    private long fadeStartTime = 0;
    private static final float FADE_DURATION = 1.0f; // 渐变周期（秒）
    //public TextureRegion currentRegion;
    private static final UIStrings MushRoomUIs = CardCrawlGame.languagePack.getUIString("RedCat:MushRoomUI");
    private static final UIStrings CorruptUIs = CardCrawlGame.languagePack.getUIString("RedCat:CorruptUI");
    private static final float ICON_WIDTH = 150F*Settings.scale;
    private static final float ICON_HEIGHT = 250F*Settings.scale;
   // private int currentHigh = 0; // 当前高度
    public RedCatUI() {
        super(ByKuang,250,360,50,200);
    }


    @Override
    public void render(SpriteBatch sb) {
    }

    public void render(SpriteBatch sb, int rate) {
        //if ( currentRegion == null) return;

        sb.draw(ByTiaobtm,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
        switch (rate){
            case 1:
                sb.draw(ByTiao1,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
                break;
            case 2:
                sb.draw(ByTiao2,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
                break;
            case 3:
                sb.draw(ByTiao3,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
                break;
            case 4:
                sb.draw(ByTiao4,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
                break;
            case 5:
                sb.draw(ByTiao,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
                break;
            default:
                break;
        }
        sb.draw(ByKuangbtm,super.x-55*Settings.scale,super.y-15F,ICON_WIDTH,ICON_HEIGHT);
        if(AbstractDungeon.player.hasPower(RedMushroomBuff.POWER_ID))
            {
            // rate等于5时，实现ByKuang的alpha渐变效果
            // 1. 记录渐变开始时间（首次进入时初始化）
            if (fadeStartTime == 0) {
                fadeStartTime = TimeUtils.millis();
            }

            // 2. 计算已流逝的时间（转换为秒）
            float elapsedTime = (TimeUtils.millis() - fadeStartTime) / 1000.0f;

            // 3. 计算循环的渐变进度（0~1之间循环）
            // 使用sin函数实现平滑的淡入淡出循环，也可以用mod实现线性渐变
            float progress = (float) Math.sin(elapsedTime * Math.PI * 2 / FADE_DURATION) / 2 + 0.5f;
            // 线性渐变备选方案：float progress = (elapsedTime % FADE_DURATION) / FADE_DURATION;

            // 4. 保存当前SpriteBatch的颜色状态（避免影响其他绘制）
            Color originalColor = sb.getColor().cpy();

            // 5. 设置渐变的alpha值（0.0=完全透明，1.0=完全不透明）
            sb.setColor(originalColor.r, originalColor.g, originalColor.b, progress);

            // 6. 绘制带透明度的ByKuang
            sb.draw(ByKuang, super.x-55*Settings.scale, super.y-15F, ICON_WIDTH, ICON_HEIGHT);

            // 7. 恢复原始颜色状态
            sb.setColor(originalColor);
        }

        //super.render(sb);

    }

    @Override
    protected void onHover() {
        TipHelper.renderGenericTip(
                InputHelper.mX + 20.0F * Settings.scale,
                InputHelper.mY,
                CorruptUIs.TEXT[0],
                CorruptUIs.TEXT[1]+CorruptUIs.TEXT[2]+ ((RedCat)AbstractDungeon.player).var+MushRoomUIs.TEXT[0]+MushRoomUIs.TEXT[1]
                );

//        TipHelper.renderGenericTip(
//                InputHelper.mX + 20.0F * Settings.scale +300F*Settings.scale,
//                InputHelper.mY,
//                MushRoomUIs.TEXT[0],
//                MushRoomUIs.TEXT[1]
//        );

    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }
}
