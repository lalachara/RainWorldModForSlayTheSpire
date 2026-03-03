package org.example.cards.RedCat.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.example.Character.RedCat;
import org.example.cards.RedCat.RedCatCard;

import static org.example.Character.SlugCat.Enums.WorkReward_Tag;

public class RedCat_Save extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Save";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Save.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Save() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 12;
        //this.magicNumber = this.baseMagicNumber = 2;
        //tags.add(WorkReward_Tag);
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {




        this.calculateCardDamage(null);

        addToBot(new DamageAllEnemiesAction(p,
                this.multiDamage,
                DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.LIGHTNING
        ));

        if(p instanceof RedCat){
            RedCat rc = (RedCat) p;
            rc.addCorrupt(-5);
        }

    }

//    /*    */   public void applyPowers() {
//        int amount = 0;
//        /* 48 */    if(AbstractDungeon.player instanceof RedCat){
//            RedCat rc = (RedCat) AbstractDungeon.player;
//                amount = rc.var*magicNumber;
//
//        }
//                    this.baseDamage+=amount;
//        /*    */
//        /* 53 */     super.applyPowers();
//        /*    */
//        /* 55 */     this.baseDamage-=amount;
//        /*    */   }
//    /*    */
//    /*    */
//    /*    */   public void calculateCardDamage(AbstractMonster mo) {
//        int amount = 0;
//        /* 48 */    if(AbstractDungeon.player instanceof RedCat){
//            RedCat rc = (RedCat) AbstractDungeon.player;
//                amount = rc.var*magicNumber;
//
//        }
//        this.baseDamage+=amount;
//        /*    */
//        /* 53 */     super.calculateCardDamage(mo);
//        /*    */
//        /* 55 */     this.baseDamage-=amount;
//        /*    */   }
//

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeDamage(2);
            upgradeName();
            initializeDescription();
        }
    }
}