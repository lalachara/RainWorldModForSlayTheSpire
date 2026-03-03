package org.example.cards.RedCat.attacks;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.SpearDamageAction;
import org.example.Actions.WhenKillEnemyAction;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.ChuangBuff;

import static org.example.Character.SlugCat.Enums.*;

public class RedCat_Break extends RedCatCard {
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Break";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Break.png";
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
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Break() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.damage = this.baseDamage = 10;
        this.exhaust = true;
        tags.add(WorkReward_Tag);
        initializeDescription();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        CardCrawlGame.sound.play("Liver:Spear");
        int enemyCount = 0;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                enemyCount++;
            }
        }
        for (int i = 1; i < enemyCount; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }

        AbstractDungeon.actionManager.addToBottom(new WhenKillEnemyAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),4));


    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            this.upgradeDamage(2);
            upgradeName();
        }
    }
}