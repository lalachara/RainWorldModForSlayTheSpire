package org.example.tools;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.example.cards.Liver.attacks.*;
import org.example.powers.ChiefBuff;
import org.example.powers.FreeSpearBuff;
import org.example.powers.RedCat.Inveteracy;

import java.util.Objects;

import static org.example.Character.SlugCat.Enums.*;

public class Tools {
    private static final String[] Cardlist = new String[]{
            Liver_Spear.ID,
            Liver_Spear.ID,
            Liver_SpearBoom.ID,
            Liver_SpearDouble.ID,
            Liver_SpearElec.ID,
            Liver_SpearStab.ID,
            Liver_SpearSkip.ID,
            Liver_SpearSkip.ID,
            Liver_SpearStab.ID,
            Liver_SpearStabHead.ID,
            Liver_SpearThroat.ID



    };
    public static boolean hasCard(String cardId) {
        return AbstractDungeon.player.masterDeck.group.stream()
                .anyMatch(card -> card.cardID.equals(cardId));
    }
    public static boolean CorruptCard(AbstractCard c) {
        if(!c.hasTag(Corrupt_Tag)&& !c.hasTag(CantCorrupt_Tag)) {
            c.tags.add(Corrupt_Tag);
            c.glowColor = Color.PURPLE;
            c.initializeDescription();
//            if(AbstractDungeon.player.hasPower(Inveteracy.POWER_ID)){
//                AbstractDungeon.player.getPower(Inveteracy.POWER_ID).onSpecificTrigger();
//            }
            return true;
        }
        return false;
    }
    public static boolean DeCorruptCard(AbstractCard c) {
        if(c.hasTag(Corrupt_Tag)) {
            c.tags.remove(Corrupt_Tag);
            c.glowColor = new Color(0.2F, 0.9F, 1.0F, 0.25F);

            return true;
        }
        return false;
    }
    public static AbstractCard getCardByID(CardGroup cardGroup,String cardID){
        for (int i = 0; i < cardGroup.size(); i++) {
            if(Objects.equals(cardGroup.group.get(i).cardID, cardID))
                return cardGroup.group.get(i);
        }
        return null;
    }

    public static AbstractCard GetRandomSpearAndAddTag(boolean isuograde){
        int i = AbstractDungeon.cardRandomRng.random(10);

        AbstractCard card = CardLibrary.getCard(Cardlist[i]).makeCopy();
        if(isuograde)
            card.upgrade();
        card.tags.add(TreasureSpear_TAG);
        card.retain = true;
        if(card.hasTag(Treasure_TAG)&&AbstractDungeon.player.hasPower(ChiefBuff.POWER_ID))
            card.setCostForTurn(0);
        if(AbstractDungeon.player.hasPower(FreeSpearBuff.POWER_ID))
            card.setCostForTurn(0);
        card.glowColor = Color.GOLD.cpy();;

        return card;
    }

    public static void saveRegionToFile(TextureRegion region, String filePath) {
        // 创建Pixmap并从region中读取像素
        Texture texture = region.getTexture();
        texture.getTextureData().prepare();
        Pixmap pixmap = texture.getTextureData().consumePixmap();

        // 裁剪出region部分
        Pixmap regionPixmap = new Pixmap(region.getRegionWidth(), region.getRegionHeight(), pixmap.getFormat());
        regionPixmap.drawPixmap(
                pixmap,
                0, 0, // 目标起点
                region.getRegionX(), region.getRegionY(), // 源起点
                region.getRegionWidth(), region.getRegionHeight() // 区域大小
        );

        // 保存为PNG
        PixmapIO.writePNG(new FileHandle(filePath), regionPixmap);

        // 释放资源
        regionPixmap.dispose();
        pixmap.dispose();
    }



    public void GetImageResource(TextureRegion img,String savepath){
        Tools.saveRegionToFile(img,"savepath");
    }
}
