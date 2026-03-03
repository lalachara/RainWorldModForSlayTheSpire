package org.example.cards.RedCat.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.RedCat;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.CorruptStrengthPower;
import org.example.powers.RedCat.RedMushroomBuff;

import static org.example.Character.SlugCat.Enums.*;

public class RedCat_Corrupt extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Corrupt";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Corrupt.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 0;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.NONE;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Corrupt() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        //this.selfRetain = true;
        this.exhaust = true;
        this.tags.add(Corrupt_Tag);
        this.magicNumber = this.baseMagicNumber =3;
        this.initializeDescription();

}

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//            //return p.hasPower(RedMushroomBuff.POWER_ID)||super.canUse(p,m);
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        {
            addToBot(new AddTemporaryHPAction(p, p, magicNumber));
        }

}
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeMagicNumber(1);

            upgradeName();
            initializeDescription();
        }
    }


}