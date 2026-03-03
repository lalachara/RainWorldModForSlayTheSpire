package org.example.cards.Liver.attacks;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.*;

import static org.example.Character.SlugCat.Enums.*;


public class Liver_FoodHit extends CustomCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:FoodHit";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_FoodHit.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_FoodHit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 5;
        this.magicNumber = this.baseMagicNumber = 1;


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("Liver:Strike");
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {

        super.calculateCardDamage(mo);
        if(AbstractDungeon.player instanceof SlugCat){
            SlugCat liver = (SlugCat) AbstractDungeon.player;
            damage += liver.food*magicNumber;
        }
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1); // 升级后增加1点
            upgraded = true;
        }
    }
}