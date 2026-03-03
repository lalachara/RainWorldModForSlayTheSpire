package org.example.cards.Patch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


//DeepSeek写出来的代码，牛逼
public class CardHoverPatch {
    private static final Logger logger = LogManager.getLogger(CardHoverPatch.class.getName());
    @SpirePatch(clz = AbstractCard.class, method = "renderHoverShadow")
    public static class RenderPatch {
        private static final float PREVIEW_SCALE = 0.75f;
        private static final float X_OFFSET = 70f;
        private static final float Y_OFFSET = -80f;

        @SpirePostfixPatch
        public static void onHoverRender(AbstractCard __instance, SpriteBatch sb) {
            // 仅当卡牌处于手牌且悬停时触发
            if (AbstractDungeon.player != null  &&
                    __instance instanceof MultiPreviewCard) {

                MultiPreviewCard mpc = (MultiPreviewCard)__instance;
                if (!mpc.previewCardIDs.isEmpty()) {
                    renderPreviews(sb, mpc);
                }
            }
        }

        private static void renderPreviews(SpriteBatch sb, MultiPreviewCard card) {

            ArrayList<AbstractCard> previews = card.getPreviewCards();
            if (previews.isEmpty()) return;


            float cardWidth = AbstractCard.IMG_WIDTH * PREVIEW_SCALE;
            // 自动向左偏移
            float totalWidth = previews.size() * (cardWidth);

            float baseX = card.current_x - X_OFFSET- totalWidth;
            float baseY = card.current_y + Y_OFFSET;

            if (baseX < 0) {
                baseX = card.current_x + 370f;
            }

            // 动态Y位置
            baseY = Math.min(baseY, Settings.HEIGHT - 200f);

            for (int i = 0; i < previews.size(); i++) {
                AbstractCard preview = previews.get(i);
                preview.current_x = baseX + (i * cardWidth ); // 调整为向左排列
                preview.current_y = baseY;
                preview.drawScale = PREVIEW_SCALE;
                preview.render(sb);
            }
        }
    }
}