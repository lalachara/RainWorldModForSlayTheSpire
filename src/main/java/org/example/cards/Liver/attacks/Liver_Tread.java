package org.example.cards.Liver.attacks;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.powers.ChuangBuff;

import static org.example.Character.SlugCat.Enums.Liver_Color;


public class Liver_Tread extends CustomCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:Tread";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_Tread.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_Tread() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage  = this.baseDamage = 10;


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("BLUNT_FAST");
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        ;if(m.hasPower(ChuangBuff.POWER_ID)){
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(1));
        }
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeDamage(4);
            upgradeName();
        }
    }
}