package org.example.cards.Liver.skills;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.UUID;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS;


public class ColorLess_VoidHand extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Lalacha:VoidHand";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Voidhand.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = COLORLESS;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.NONE;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ColorLess_VoidHand() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    exhaust = true; // 设置卡牌为消耗
}

//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        int amount = p.exhaustPile.size();
//        if(amount>0){
//            amount = 10;
//        }
//        if (amount > 0) {
//            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, amount));
//        }
//
//
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = p.exhaustPile.size();
        // 记录当前手牌状态
        ArrayList<UUID> originalHand = new ArrayList<>();
        for (AbstractCard c : p.hand.group) {
            originalHand.add(c.uuid);
        }

        // 计算需要抽牌的数量（动态适配手牌上限）
        int drawAmount = Math.min(amount,10 - p.hand.size());

        // 执行抽牌动作
        if (drawAmount > 0) {
            addToBot(new DrawCardAction(drawAmount));
        }

        // 添加延迟动作处理新抽的牌
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> newCards = new ArrayList<>();

                // 识别所有新抽的卡牌
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (!originalHand.contains(c.uuid)) {
                        newCards.add(c);
                    }
                }

                // 动态处理实际抽到的卡牌（可能因牌库不足少于预期）
                int actualDraw = Math.min(newCards.size(), drawAmount);
                for (int i = 0; i < actualDraw; i++) {
                    AbstractCard card = newCards.get(i);
                    card.setCostForTurn(0);
                    card.superFlash(Color.GOLD.cpy());

                    // 防止被其他效果覆盖（可选）
                    //card.retain = true; // 保留状态
                    card.isCostModifiedForTurn = true;
                }

                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeName();
        upgradeBaseCost(1);
        initializeDescription(); // 更新描述
    }
}
}