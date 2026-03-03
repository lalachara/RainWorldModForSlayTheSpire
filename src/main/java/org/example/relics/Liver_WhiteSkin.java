package org.example.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.example.Character.*;


public class Liver_WhiteSkin extends CustomRelic {
    public static final String ID = "Liver:WhiteSkin";
    private static final String IMG_PATH = "images/CharacterImg/Relics/whiteskin.png";
    private static final String IMG_OL_PATH = "images/CharacterImg/Relics/outline/whiteskin.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;



    public Liver_WhiteSkin() {
        super(ID, ImageMaster.loadImage(IMG_PATH),ImageMaster.loadImage(IMG_OL_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        counter = 1;
    }

    @Override
    public void update() {
        super.update();
        if(hb.hovered &&AbstractDungeon.currMapNode!=null&& InputHelper.justClickedRight&&AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            if(counter>0)
            {
                if(AbstractDungeon.player instanceof SlugCat)
                {

                    if(EnergyPanel.totalCount>0)
                    {
                        System.out.println(EnergyPanel.totalCount);
                        counter--;
                        addToBot(new LoseEnergyAction(1));
                        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new IntangiblePlayerPower(AbstractDungeon.player,1),1));
                    }
                    else {
                        AbstractDungeon.effectList.add(
                                new ThoughtBubble(
                                        AbstractDungeon.player.dialogX,
                                        AbstractDungeon.player.dialogY,
                                        2.0F, // 持续时间
                                        this.DESCRIPTIONS[1], // 你想让角色说的话
                                        true // 是否朝右
                                )
                        );
                    }
                }

            }
            else{
                AbstractDungeon.effectList.add(
                        new ThoughtBubble(
                                AbstractDungeon.player.dialogX,
                                AbstractDungeon.player.dialogY,
                                2.0F, // 持续时间
                                this.DESCRIPTIONS[2], // 你想让角色说的话
                                true // 是否朝右
                        )
                );
            }

        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player instanceof SlugCat;
    }

}