package org.example.cards.RedCat.skills;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.example.Character.Liver;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.RedCatCard;
import org.example.monsters.FireCent;

import static org.example.Character.SlugCat.Enums.Liver_Color;
import static org.example.Character.SlugCat.Enums.RedCat_Color;


public class RedCat_SmallCent extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:SmallCent";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_SmallCent.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 0;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_SmallCent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE,  RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p, magicNumber), magicNumber));
        if(p instanceof SlugCat){
            SlugCat player = (SlugCat)p;
            player.addFood(2);
        }
        for (AbstractMonster mo : com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()&&mo instanceof FireCent) {
                addToBot(new ApplyPowerAction(mo,p,new StrengthPower(mo, AbstractDungeon.ascensionLevel >= 18?6:4), AbstractDungeon.ascensionLevel >= 18?6:4));

                AbstractDungeon.actionManager.addToBottom(new ShoutAction(mo, cardStrings.UPGRADE_DESCRIPTION, 3F, 3F));
            }
        }
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeMagicNumber(1);
            upgradeName();
        }
    }
}