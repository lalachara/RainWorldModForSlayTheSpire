package org.example.cards.RedCat.attacks;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.example.Actions.SpearDamageAction;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.ChuangBuff;
import org.example.powers.NimbleBuff;

import static org.example.Character.SlugCat.Enums.Liver_Color;
import static org.example.Character.SlugCat.Enums.Spear_TAG;

public class RedCat_SpearThroat extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:SpearThroat";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_SpearThroat.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 2 ;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_SpearThroat() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE,  RARITY, TARGET);
        this.damage  = this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(Spear_TAG);
        initializeDescription();


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("Liver:Spear");
        addToBot(new SpearDamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS)));
        addToBot(new ApplyPowerAction(
                p, p, new VigorPower(p, this.magicNumber), this.magicNumber
        ));
        addToBot(new ApplyPowerAction(m,p,new ChuangBuff(m,magicNumber),magicNumber));

    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeBaseCost(1);
            upgradeName();
        }
    }
}