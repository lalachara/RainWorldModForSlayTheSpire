package org.example.cards.RedCat.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.example.Actions.ReturnHandAction;
import org.example.Actions.SelectCardsInHandCorruptAction;
import org.example.Actions.SelectHandCardCorrupt;
import org.example.Character.RedCat;
import org.example.cards.RedCat.RedCatCard;
import org.example.powers.DeepCorrupt;
import org.example.tools.Tools;

import static org.example.Character.SlugCat.Enums.*;

public class RedCat_OriginalCorrupt extends RedCatCard
{
    //卡牌ID，命名规则: mod名:卡牌名
    public static final String ID = "RedCat:OriginalCorrupt";
    //卡牌插画路径
    public static final String IMG_PATH = "images/CharacterImg/Cards/RedCat/OCorrupt.png";
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
    private static final CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    //卡牌目标 SELF,ENEMY,ALL_ENEMY,ALL,NONE,SELF_AND_ENEMY;
    private static final CardTarget TARGET = CardTarget.NONE;

    //private boolean isusedthisturn = false;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public RedCat_OriginalCorrupt() {
    super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
    //this.tags.add(CantRemoved_TAG);
        this.tags.add(Corrupt_Tag);

        initializeDescription();
}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectHandCardCorrupt());
//        if(!isusedthisturn&&upgraded){
//            addToBot(new ReturnHandAction(this));
//        }
        //isusedthisturn = true;
        //addToBot(new MakeTempCardInDrawPileAction(new RedCat_Corrupt(),1,true,true));

}

    @Override
    public void atTurnStart() {
        //isusedthisturn = false;
    }
//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        return false;
//    }

//    @Override
//    public boolean canUpgrade() {
//        return false;
//    }

//    @Override
//    public void triggerOnEndOfPlayerTurn() {
//        AbstractCard c1 = null,c2 = null;
//        if(AbstractDungeon.player.hand.group.contains(this)){
//            int index = -1;
//            for (int i = 0; i < AbstractDungeon.player.hand.group.size(); i++) {
//                if(AbstractDungeon.player.hand.group.get(i)==this) {
//                    index = i;
//                    break;
//                    }
//                }
//            if(index!=-1){
//                if(index-1>=0){
//                    c1 = AbstractDungeon.player.hand.group.get(index-1);
//                }
//                if(index+1<AbstractDungeon.player.hand.group.size()){
//                    c2 = AbstractDungeon.player.hand.group.get(index+1);
//                }
//            }
//            {
//                if(c1!=null){
//                    Tools.CorruptCard(c1);
//                }
//                if(c2!=null){
//                    Tools.CorruptCard(c2);
//                }
//            }
//        }
//
//
//
//    }




    @Override
    public void upgrade() {
    //卡牌升级后的效果
    if (!this.upgraded) {
        //this.rawDescription = cardStrings.UPGRADE_DESCRIPTION; // 更新描述
        this.selfRetain = true;
        upgradeName();
        initializeDescription();
    }
}
}