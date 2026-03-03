package org.example.cards.RedCat.attacks;


import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.RedCat.EatPower;
import org.example.powers.RedCat.RedMushroomBuff;

import static org.example.Character.SlugCat.Enums.Corrupt_Tag;

public class RedCat_Eat extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Eat";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Eat.png";
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
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Eat() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE,  RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 20;
        this.exhaust = true;
        this.tags.add(Corrupt_Tag);
        initializeDescription();
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean eat = false;
        int amount = AbstractDungeon.player.currentHealth+ TempHPField.tempHp.get(AbstractDungeon.player);
        for (AbstractMonster mo : com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()&&mo.currentHealth+TempHPField.tempHp.get(mo)<amount) {
                addToTop(new InstantKillAction(mo));
                eat =true;
            }
        }
        if(eat)
            CardCrawlGame.sound.play("RedCat:Eat");

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hasPower(RedMushroomBuff.POWER_ID);
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeBaseCost(0);
        upgradeName();
        initializeDescription();
    }
}
}