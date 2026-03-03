package org.example.cards.Liver.skills;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.powers.Inherent;

import static org.example.Character.SlugCat.Enums.Liver_Color;

public class Liver_Wawawawa extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:Wawawawa";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_Wawawawa.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_Wawawawa() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.magicNumber = this.baseMagicNumber = 2; // 设置初始值
        this.exhaust = true; // 设置卡牌为消耗卡
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 给所有敌人99层虚弱
        CardCrawlGame.sound.play("Liver:WAAAAAA");
        for (AbstractMonster mo : com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(
                        mo, p, new com.megacrit.cardcrawl.powers.WeakPower(mo, 99, false), 99));
            }
        }
        if(p.hasPower(Inherent.POWER_ID)){
            ((Inherent)p.getPower(Inherent.POWER_ID)).wawawa = true;
        }
        // 自身失去2点力量
        //addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, -1*magicNumber), -1*magicNumber));
    }


    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeBaseCost(0);
        upgradeName();
    }
}
}