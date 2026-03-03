package org.example.cards.Liver.skills;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.cards.Liver.others.Liver_stubborn;
import org.example.powers.BecauseBuff;
import org.example.powers.CatComingBuff;
import org.example.powers.WorkErrorBuff;
import org.example.relics.Liver_Eternal;
import org.example.tools.Tools;

import static org.example.Character.SlugCat.Enums.Liver_Color;

public class Liver_TwistFate extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:TwistFate";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_TwistFate.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 0;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.SKILL;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_TwistFate() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    //this.isEthereal = true; // 虚无
        this.exhaust  = true;
        this.bannerSmallRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_RARE);
        this.bannerLargeRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_RARE_L);
        if(AbstractDungeon.player!=null){
            if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)){
                bannerSmallRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special.png"));
                bannerLargeRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special_L.png"));
            }

        }

        this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
        this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof SlugCat){
            SlugCat liver = (SlugCat) p;
            liver.workLevel = Tools.hasCard(Liver_stubborn.ID)?liver.maxWorkLevel-1:liver.maxWorkLevel;
//            if(liver.workLevel==9&&p.hasPower(WorkErrorBuff.POWER_ID)){
//                AchievementMgr.unlockAchievement(13);
//            }
            if(liver.hasPower(CatComingBuff.POWER_ID)){
                liver.getPower(CatComingBuff.POWER_ID).onSpecificTrigger();
            }
        }
        addToBot(new ApplyPowerAction(p,p,new BecauseBuff(p,1),1));
        addToBot(new ApotheosisAction());
}

    @Override
    public void initializeDescription() {
        if(AbstractDungeon.player!=null)
            this.rawDescription = (upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION)+(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)?cardStrings.EXTENDED_DESCRIPTION[1]:cardStrings.EXTENDED_DESCRIPTION[0]);
        else
            this.rawDescription = (upgraded?cardStrings.UPGRADE_DESCRIPTION:cardStrings.DESCRIPTION)+cardStrings.EXTENDED_DESCRIPTION[0];
        super.initializeDescription();
    }

    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        upgradeName();
        this.selfRetain = true;
        this.isEthereal = false;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; // 更新描述
        initializeDescription(); // 刷新卡牌描述

    }
}
}