package org.example.cards.Liver.powers;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.example.Character.*;
import org.example.powers.WorkErrorBuff;
import org.example.powers.WorkLevelLockBuff;
import org.example.relics.Liver_Eternal;

import static org.example.Character.SlugCat.Enums.Liver_Color;


public class Liver_WorkError extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "Liver:WorkError";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/Liver_WorkError.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    //卡牌cost
    private static final int COST = 0;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.POWER;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = Liver_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Liver_WorkError() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.misc = 0;
        if(AbstractDungeon.player instanceof SlugCat)
            this.misc = ((SlugCat)AbstractDungeon.player).kills;
        this.glowColor = Color.RED.cpy();;
        this.frameSmallRegion = ImageMaster.CARD_FRAME_POWER_RARE;
        this.frameLargeRegion = ImageMaster.CARD_FRAME_POWER_RARE_L;
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
        if(p instanceof SlugCat){
            int kills = ((SlugCat)p).kills;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, kills), kills));
        }

        CardCrawlGame.sound.play("Liver:WorkError");
        addToBot(new ApplyPowerAction(p,p,new WorkErrorBuff(p,1),1));
        addToBot(new ApplyPowerAction(p,p,new WorkLevelLockBuff(p,p.hasRelic(Liver_Eternal.ID)?9:1),p.hasRelic(Liver_Eternal.ID)?99:1));

    }


    @Override
    public void initializeDescription() {
        int i = 1;
        if(AbstractDungeon.player!=null)
            if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)){
            i = 9;
        }
        if(AbstractDungeon.player instanceof SlugCat)
            this.misc = ((SlugCat)AbstractDungeon.player).kills;
        if(upgraded)
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] +cardStrings.EXTENDED_DESCRIPTION[1] + this.misc +cardStrings.EXTENDED_DESCRIPTION[2] + i+cardStrings.EXTENDED_DESCRIPTION[3];
        else
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + this.misc +cardStrings.EXTENDED_DESCRIPTION[2]+i+cardStrings.EXTENDED_DESCRIPTION[3] ;

        super.initializeDescription();
    }


    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true; // 设置卡牌为固有
            this.initializeDescription();

        }
    }
}