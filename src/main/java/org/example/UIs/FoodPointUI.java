package org.example.UIs;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.example.Character.SlugCat;
import org.example.powers.OverSleepDeBuff;
import org.example.relics.Liver_SleepPotions;

public class FoodPointUI extends ClickableUIElement {
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("Liver:FoodPoint");
    private static final float hb_w = 275.0F;
    private static final float hb_h = 25.0F;

    private int food;
    private int sleepfood;
    private int maxfood;
//    private static float baseX = 30.0F * Settings.scale;
//    private static float baseY = 195.0F * Settings.scale;
    private static final Texture foodPointFullImage, foodPointEmptyImage,foodPointCutImage;
    public FoodPointUI() {
        super(foodPointFullImage,96,292,hb_w,hb_h);
//        super.x = (int)(Settings.M_W*0.05);
//        super.y = (int)(Settings.M_H*0.27);
       // hitbox = new Hitbox(super.x, super.y, hb_w, hb_h);
        setClickable(true);
    }

    @Override
    protected void onHover() {
        if (AbstractDungeon.player instanceof SlugCat &&AbstractDungeon.getCurrRoom() != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            TipHelper.renderGenericTip(
                    InputHelper.mX + 20.0F * Settings.scale,
                    InputHelper.mY,
                    UIs.TEXT[0],
                    UIs.TEXT[1]+food+"/"+maxfood
            );
        }

    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }
    public void DrawFoodPoint(SpriteBatch sb,int i,boolean full){
        if(full) {
            sb.draw(foodPointFullImage, super.x + i * 25.0F * Settings.scale, super.y, 25.0F * Settings.scale, 25.0F * Settings.scale);
        } else {
            sb.draw(foodPointEmptyImage, super.x + i * 25.0F * Settings.scale, super.y, 25.0F * Settings.scale, 25.0F * Settings.scale);
        }
    }

    @Override
    public void update() {
        super.update();

//        if (hitbox.hovered && InputHelper.justClickedLeft) {
//            trySleep();
//        }
    }


    @Override
    public void render(SpriteBatch sb){
        if(AbstractDungeon.player instanceof SlugCat) {
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            food = liver.food;
            maxfood = liver.maxFood;
            sleepfood = liver.sleepfood;
            if(liver.hasRelic(Liver_SleepPotions.ID))
                sleepfood = 3;

            if(liver.hasPower(OverSleepDeBuff.POWER_ID))
                sleepfood = maxfood;


            for (int i = 0; i < maxfood+1; i++) {

                    if(i<sleepfood){
                        DrawFoodPoint(sb, i, i < food);
                    }else if(i==sleepfood)
                        sb.draw(foodPointCutImage, super.x + i * 25.0F * Settings.scale, super.y, 25.0F * Settings.scale, 25.0F * Settings.scale);
                    else{
                        DrawFoodPoint(sb,i,i-1<food);
                    }

                }


            super.renderHitbox( sb);


        }





    }
    static {
        // Load the food point image
        foodPointFullImage = new Texture("images/CharacterImg/RainWorld/WorkLevels/FoodPointFull.png");
        foodPointEmptyImage = new Texture("images/CharacterImg/RainWorld/WorkLevels/FoodPointEmpty.png");
        foodPointCutImage = new Texture("images/CharacterImg/RainWorld/WorkLevels/FoodPointCut.png");

    }
}
