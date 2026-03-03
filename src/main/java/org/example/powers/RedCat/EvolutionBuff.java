package org.example.powers.RedCat;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;


public class EvolutionBuff extends AbstractPower {
    public static final String POWER_ID = "RedCat:EvolutionBuff";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final String IMG_128 = "images/CharacterImg/Powers/RedCat/EvolutionBuff_128.png";
    private static final String IMG_48 = "images/CharacterImg/Powers/RedCat/EvolutionBuff_48.png";
    private Dictionary<String, Integer> MonsterList;

    public EvolutionBuff(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.description = DESCRIPTIONS[0];
        MonsterList = new java.util.Hashtable<String, Integer>();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_128), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_48), 0, 0, 48, 48);

        this.updateDescription();
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner instanceof AbstractMonster&&damageAmount>0)
        {
            AbstractMonster m = (AbstractMonster) info.owner;
            if(MonsterList.get(m.name)!=null)
            {
                if(damageAmount-(MonsterList.get(m.name))>0){
                    MonsterList.put(m.name,MonsterList.get(m.name)+amount);
                    updateDescription();
                }

                return damageAmount-(MonsterList.get(m.name));
            }
            else
            {
                MonsterList.put(m.name,amount);
                updateDescription();
                return damageAmount;
            }
        }
        return damageAmount;
    }

//    public void atStartOfTurn() {
//        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(MonsterList.size()>0)
        {
            List<String> keys = Collections.list(MonsterList.keys());
            description =description+" NL "+DESCRIPTIONS[1];
            for (int i = 0; i < keys.size(); i++) {
            description += DESCRIPTIONS[2]+ keys.get(i)+DESCRIPTIONS[3]+MonsterList.get(keys.get(i))+" ." ;
            }

        }

    }
}
