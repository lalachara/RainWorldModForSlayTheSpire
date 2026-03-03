package org.example.cards.Liver.attacks;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.powers.NimbleBuff;

import static org.example.Character.SlugCat.Enums.Liver_Color;

public class Liver_TailAtk extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:TailAtk";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_TailAtk.png";
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
    private static final CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_TailAtk() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.damage = this.baseDamage = 8;
    this.magicNumber = this.baseMagicNumber = 2;
    this.cardsToPreview = new Slimed(); // 设置预览卡牌

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("Liver:Strike");
        // 造成伤害
        addToBot(new com.megacrit.cardcrawl.actions.common.DamageAction(
                m,
                new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn),
                com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
        ));

        // 获得 NimbleBuff
        addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(
                p, p, new NimbleBuff(p, this.magicNumber), this.magicNumber
        ));

        // 将1张粘液牌加入弃牌堆
        addToBot(new com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction(
                new Slimed(), 1
        ));
    }


    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeDamage(2);
        upgradeMagicNumber(1);

        upgradeName();
    }
}
}