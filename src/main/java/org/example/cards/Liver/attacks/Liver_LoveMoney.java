package org.example.cards.Liver.attacks;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.WhenKillEnemyAction;

import static org.example.Character.SlugCat.Enums.*;

public class Liver_LoveMoney extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:LoveMoney";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_LoveMoney.png";
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
    private static final CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    int lastdamage = 0;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_LoveMoney() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 0;


}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WhenKillEnemyAction(m, new DamageInfo(p, this.damage+getTreasureAmount(), this.damageTypeForTurn),2));
        CardCrawlGame.sound.play("Liver:LoveGold");
    }
    @Override
    public void triggerWhenDrawn() {
        super.initializeDescription();
    }
    @Override
    public void initializeDescription() {
        this.rawDescription = cardStrings.DESCRIPTION +  getTreasureAmount();

        super.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if(lastdamage!=getTreasureAmount())
        {
            lastdamage = getTreasureAmount();
            initializeDescription();
        }
    }

    public int getTreasureAmount(){
        int amount = 0;
        if(AbstractDungeon.player!=null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(Treasure_TAG)) {
                    amount += 2;
                }
            }
            // 遍历抽牌堆
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.hasTag(Treasure_TAG)) {
                    amount += 2;
                }
            }
            // 遍历弃牌堆
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.hasTag(Treasure_TAG)) {
                    amount += 2;
                }
            }
            // 遍历暂时牌堆
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c.hasTag(Treasure_TAG)) {
                    amount += 2;
                }
            }
            amount += (AbstractDungeon.player.gold / 40)*2;
        }
        return amount;
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        this.upgradeBaseCost(0);
        upgradeName();
    }
}
}