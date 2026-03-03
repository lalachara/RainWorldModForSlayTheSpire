package org.example.UIs;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;
import org.example.powers.OverSleepDeBuff;
import org.example.relics.Liver_SleepPotions;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.SLEEP;

public class SleepUI extends ClickableUIElement {
    private static final UIStrings UIs = CardCrawlGame.languagePack.getUIString("Liver:Sleep");

    public int picnum = 0;
    private final float size = 110;
    private SleepUIEffect effect;

//    private static float baseX = 30.0F * Settings.scale;
//    private static float baseY = 195.0F * Settings.scale;
    private static final Texture SleepButton, SleepButton1,SleepButton2,SleepDown,Sleepvfx;
    private TextureRegion glowImg = new TextureRegion(Sleepvfx);
    public SleepUI() {
       // super(SleepButton,1630,160,64,64);
        super(SleepButton,95,470,110,110);
        setClickable(false);
        effect = new SleepUIEffect(this.hitbox,glowImg, new Color(0.2F, 0.9F, 1.0F, 0.25F));
    }

    @Override
    protected void onHover() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&!AbstractDungeon.isScreenUp) {
            String body = UIs.TEXT[1] + (AbstractDungeon.player.hasRelic(Liver_SleepPotions.ID) ? 3 : 4) + UIs.TEXT[2];
            if (picnum != 0)
                body = body + " NL " + UIs.TEXT[4] + picnum + UIs.TEXT[5];
            TipHelper.renderGenericTip(
                    super.x - 100F * Settings.scale,
                    super.y + 300.0F * Settings.scale,
                    UIs.TEXT[0],
                    body
            );
        }
    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT&& !AbstractDungeon.isScreenUp)
        {
            trySleep();
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                if(m.intent==SLEEP){
                    AchievementMgr.unlockAchievement(32);
                    break;
                }
            }

        }
    }

    @Override
    public void update() {
        super.update();
        effect.update();

    }
    public boolean cansleep() {
        if (AbstractDungeon.player instanceof SlugCat) {
            SlugCat player = (SlugCat) AbstractDungeon.player;
            boolean haveDebuff = player.hasPower(OverSleepDeBuff.POWER_ID);
            return (haveDebuff && player.food == player.maxFood) || (!haveDebuff && player.food >= (player.hasRelic(Liver_SleepPotions.ID) ? player.sleepfood-1 : player.sleepfood));
        }else
            return false;
    }
    public void trySleep(){
        //System.out.println("try sleep");
        boolean canuse = false;
        if(AbstractDungeon.player instanceof SlugCat){
            SlugCat player = (SlugCat)AbstractDungeon.player;
            boolean haveDebuff = player.hasPower(OverSleepDeBuff.POWER_ID);
            if(cansleep()){
                canuse = true;
                player.sleep(haveDebuff);
            }

        if(canuse)
        {
            picnum = 3;
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
        }else {
            AbstractDungeon.effectList.add(
                    new ThoughtBubble(
                            AbstractDungeon.player.dialogX,
                            AbstractDungeon.player.dialogY,
                            2.0F, // 持续时间
                            UIs.TEXT[3], // 你想让角色说的话
                            true // 是否朝右
                    )
            );
        }

        }

    }

    @Override
    public void render(SpriteBatch sb){
        boolean isPlayerTurn = AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !AbstractDungeon.actionManager.turnHasEnded;
        if(AbstractDungeon.player instanceof SlugCat) {
            switch (picnum){
                case 1:
                    sb.draw(SleepButton1, super.x, super.y, size * Settings.scale, size * Settings.scale);
                    setClickable(false);
                    break;
                case 2:
                case 3:
                    sb.draw(SleepButton2, super.x, super.y, size * Settings.scale, size * Settings.scale);
                    setClickable(false);
                    break;
                default:
                    if(isPlayerTurn&&cansleep()){
                        effect.render(sb);
                        sb.setColor(Color.WHITE);
                        sb.draw(SleepButton, super.x, super.y, size * Settings.scale, size * Settings.scale);
                        setClickable(true);
                    }
                    else{
                        sb.draw(SleepDown, super.x, super.y, size * Settings.scale, size * Settings.scale);
                        setClickable(false);
                    }

                }
            }

            super.renderHitbox( sb);


    }
    static {
        // Load the food point image
        SleepButton = new Texture("images/CharacterImg/RainWorld/WorkLevels/Sleep.png");
        SleepButton1 = new Texture("images/CharacterImg/RainWorld/WorkLevels/Sleep1.png");
        SleepButton2 = new Texture("images/CharacterImg/RainWorld/WorkLevels/Sleep2.png");
        SleepDown = new Texture("images/CharacterImg/RainWorld/WorkLevels/SleepDown.png");
        Sleepvfx = new  Texture("images/CharacterImg/RainWorld/WorkLevels/Sleep_vfx.png");

    }
}
