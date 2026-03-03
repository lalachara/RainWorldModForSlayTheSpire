package org.example.cards.Liver.attacks;



import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.CheckPearlAction;
import org.example.Actions.SpearDamageAction;
import org.example.powers.ChuangBuff;

import static org.example.Character.SlugCat.Enums.*;

public class Liver_SpearElec extends CustomCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:SpearElec";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_SpearElec.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 3 ;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = AbstractCard.CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_SpearElec() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage  = this.baseDamage = 15;
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(Spear_TAG);
        this.tags.add(Treasure_TAG);

        this.cardsToPreview = new Liver_Spear(); // 设置预览卡牌

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("Liver:SpearElec");
        addToBot(new SpearDamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new ChuangBuff(m,magicNumber),magicNumber));
        AbstractCard c = this;
        addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
            @Override
            public void update() {
                c.modifyCostForCombat(-1);
                isDone = true;
            }
        });
//        misc--;
//        if(misc<=0){
//            this.exhaustOnUseOnce = true; // 本次打出后消耗
//            addToBot(new com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
//        }
        addToBot(new CheckPearlAction());
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeDamage(5);
            this.upgradeMagicNumber(1);
//            AbstractCard c = new Liver_Spear();
//            c.upgrade();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; // 更新描述
            this.initializeDescription();
            upgradeName();
        }
    }
}