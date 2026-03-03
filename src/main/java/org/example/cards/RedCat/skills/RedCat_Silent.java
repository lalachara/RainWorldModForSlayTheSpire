package org.example.cards.RedCat.skills;

import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import org.example.Achievement.AchievementMgr;
import org.example.Character.SlugCat;
import org.example.cards.RedCat.RedCatCard;
import org.example.monsters.FireCent;
import org.example.monsters.ThreeChicken;
import org.example.powers.ChuangBuff;

import java.time.Instant;

public class RedCat_Silent extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Silent";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/Silent.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 3;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;

    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.RARE;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Silent() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = 0; i < m.powers.size(); i++) {
            if(m.powers.get(i).type == AbstractPower.PowerType.BUFF){
                addToBot(new RemoveSpecificPowerAction(m, p, m.powers.get(i).ID));
            }
        }
        if(m instanceof Transient){
            AbstractDungeon.effectList.add(
                    new ThoughtBubble(
                            AbstractDungeon.player.dialogX,
                            AbstractDungeon.player.dialogY,
                            3.0F, // 持续时间
                            cardStrings.EXTENDED_DESCRIPTION[0], // 你想让角色说的话
                            true // 是否朝右
                    )
            );
            AchievementMgr.unlockAchievement(14);
        }
        if(m instanceof ThreeChicken){
            m.state.addAnimation(0, "clam", true, 0.0F);
            m.setMove((byte) 5, AbstractMonster.Intent.SLEEP);
            m.createIntent();
            ((ThreeChicken) m).wuhaihua = true;
            AbstractDungeon.effectList.add(
                    new ThoughtBubble(
                           m.hb.cX-100F,
                            m.hb.cY+100F,
                            3.0F, // 持续时间
                            cardStrings.EXTENDED_DESCRIPTION[1], // 你想让角色说的话
                            false // 是否朝右
                    )
            );
            //AbstractDungeon.actionManager.addToBottom(new ShoutAction(m, cardStrings.EXTENDED_DESCRIPTION[1], 3F, 3F));

        }
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(2);
            this.initializeDescription();

        }

    }



}