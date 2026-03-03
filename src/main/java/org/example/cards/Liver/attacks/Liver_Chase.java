package org.example.cards.Liver.attacks;



import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static org.example.Character.SlugCat.Enums.*;

public class Liver_Chase extends CustomCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:Chase";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_Chaser.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 3;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_Chase() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3; // 设置初始值
        this.baseDamage = this.damage = 4;


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < baseMagicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        // 摸 magicNumber 张牌
//        addToBot(new ApplyPowerAction(m,p,new ChuangBuff(m,magicNumber),magicNumber));
    }
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class CardUsedCounter {
        @SpirePostfixPatch
        public static void useCard(AbstractPlayer __instance, AbstractCard card) {

            System.out.println(1);

            }

        }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(1); // 升级后增加2点伤害
            this.upgradeMagicNumber(1); // 升级后增加1点
            upgraded = true;
        }
    }
}