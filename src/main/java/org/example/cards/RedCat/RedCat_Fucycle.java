package org.example.cards.RedCat;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Character.RedCat;
import org.example.Character.SlugCat;
import org.example.powers.RedCat.RedMushroomBuff;
import org.example.relics.Liver_Eternal;

public class RedCat_Fucycle extends CustomCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:Fucycle";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/fucycle.png";
    //固定，不要修改
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    //卡牌cost
    private static final int COST = -2;
    //卡牌类别 ATTACK,SKILL,POWER,STATUS,CURSE;
    private static final CardType TYPE = CardType.CURSE;
    //卡牌颜色 自定义颜色,COLORLESS,CURSE;
    private static final CardColor COLOR = RedCat.Enums.RedCat_Color;
    //卡牌稀有度 BASIC,SPECIAL,COMMON,UNCOMMON,RARE,CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.SELF;


    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_Fucycle() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE,COLOR, RARITY, TARGET);
        this.exhaust = true;

        if(AbstractDungeon.player!=null){
            this.bannerSmallRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_COMMON);
            this.bannerLargeRegion = new TextureAtlas.AtlasRegion(ImageMaster.CARD_BANNER_COMMON_L);
            if(AbstractDungeon.player.hasRelic(Liver_Eternal.ID)){
                bannerSmallRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special.png"));
                bannerLargeRegion.setRegion(ImageMaster.loadImage("images/CharacterImg/RainWorld/Card_Special_L.png"));
                this.frameSmallRegion = ImageMaster.CARD_FRAME_SKILL_RARE;
                this.frameLargeRegion = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }

        }


        initializeDescription();

    }





    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasRelic(Liver_Eternal.ID)&&p instanceof SlugCat){
            addToBot(new DrawCardAction(1));
            ((SlugCat) p).addWorkLevel(1);
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m)||p.hasRelic(Liver_Eternal.ID);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }
}