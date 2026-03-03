package org.example.cards.Liver.attacks;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Achievement.AchievementMgr;
import org.example.Character.*;
import org.example.powers.CatComingBuff;
import org.example.relics.Liver_Eternal;

import static org.example.Character.SlugCat.Enums.Liver_Color;

public class Liver_ThreeAgree extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:ThreeAgree";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_ThreeAgree.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 3;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.ATTACK;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_ThreeAgree() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
        this.frameSmallRegion = ImageMaster.CARD_FRAME_ATTACK_RARE;
        this.frameLargeRegion = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
        this.bannerSmallRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_RARE);
        this.bannerLargeRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_RARE_L);
        if(AbstractDungeon.player!=null){
            if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)){
                bannerSmallRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special.png"));
                bannerLargeRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special_L.png"));
            }

        }


    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new ExhaustPermanentlyAction(this));
        if(p instanceof SlugCat){
            CardCrawlGame.sound.play("Liver:ThreeAgree");
            for (int i = 0; i < 12; i++) {
                addToBot(new WaitAction(0.1F));
            }
            for (AbstractMonster mo : com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new InstantKillAction(mo));
                }
            }
            SlugCat liver = (SlugCat) p;
            if(!liver.hasRelic(Liver_Eternal.ID)){
                liver.workLevel = 4;
                if(liver.hasPower(CatComingBuff.POWER_ID))
                    addToBot(new DrawCardAction(2));
            }
            AchievementMgr.unlockAchievement(8);


        }


        // 将此牌从卡组中移除

    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        // 检查玩家是否是 wishdell 类
        if (p instanceof SlugCat) {
            SlugCat liver = (SlugCat) p;
            if(liver.workLevel>=9)
                return true;
            else
                this.cantUseMessage = "业力不足，无法使用。";
        }
        return false;
    }
    public void initializeDescription() {
        if(AbstractDungeon.player!=null)
            if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)){
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[upgraded?1:0];
        }
        super.initializeDescription();

    }
    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        this.isInnate = true; // 设置卡牌为固有
        upgradeName();
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription(); // 更新描述
    }
}
}