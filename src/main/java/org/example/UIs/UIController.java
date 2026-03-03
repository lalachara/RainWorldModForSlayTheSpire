package org.example.UIs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;

public class UIController {
   public FoodPointUI foodPointUI;
   public SleepUI sleepUI;
   public WorkLevelIndicator workLevelUI;
   public RedCatUI redCatUI;
    public SlugCat player;
    public UIController(SlugCat player) {
        this.player = player;
        if (player instanceof RedCat) {
            redCatUI = new RedCatUI();
        }
        if (player instanceof SlugCat) {
            foodPointUI = new FoodPointUI();
            sleepUI = new SleepUI();
            workLevelUI = new WorkLevelIndicator(player);
        }
//        foodPointUI = new FoodPointUI();
//        sleepUI = new SleepUI();

    }
    public void start() {
        if(sleepUI!=null)
            sleepUI.picnum=0;
        if(AbstractDungeon.player instanceof RedCat){
            ((RedCat)AbstractDungeon.player).var = 0;
        }

    }
    public void update() {
        if (AbstractDungeon.player instanceof RedCat) {
            if (redCatUI != null) {
                redCatUI.update();
            }
        }
        if (foodPointUI != null) {
            foodPointUI.update();
        }
        if (sleepUI != null) {
            sleepUI.update();
        }
        if (workLevelUI != null) {
            workLevelUI.update(player.workLevel);
        }
    }
    public void render(SpriteBatch sb) {
        if(AbstractDungeon.player instanceof RedCat)
        {
            RedCat redCat = (RedCat) AbstractDungeon.player;
            if (redCatUI != null) {
                redCatUI.render(sb,Math.min(redCat.var, 5));
            }
        }
        if (workLevelUI != null) {
            workLevelUI.render(sb);
        }
        if (foodPointUI != null) {
            foodPointUI.render(sb);
        }
        if (sleepUI != null) {
            sleepUI.render(sb);
        }

    }

    public void turnStart() {
        if(sleepUI!=null){
            if(sleepUI.picnum > 0) {
                sleepUI.picnum--;
            }
        }
    }
}
