package org.example.cards.RedCat.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.example.cards.RedCat.RedCatCard;


public class RedCat_RemainHot extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:RemainHot";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/RemainHot.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 1;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_RemainHot() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.block = this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 1; // 设置魔法数字
        this.haveUpgradedDescription = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new GainBlockAction(p, p, block));
        if(EnergyPanel.getCurrentEnergy()==this.costForTurn){
            addToBot(new GainEnergyAction(magicNumber));
        }
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点格挡
            upgradeName();
            upgradeBlock(2);
            upgraded = true;
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}