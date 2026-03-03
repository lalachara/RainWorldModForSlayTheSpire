//package org.example.cards.Patch;
//
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardGroup;
//import org.example.cards.RedCat.skills.RedCat_OriginalCorrupt;
//
//import static org.example.Character.SlugCat.Enums.CantRemoved_TAG;
//
//@SpirePatch(clz = CardGroup.class, method = "getPurgeableCards")
//public class CantRemovedCard2 {
//    @SpirePrefixPatch
//    public static CardGroup Prefix(CardGroup __instance) {
//        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//        for (AbstractCard c : __instance.group) {
//            // 原版排除条件
//            boolean isExcludedCurse = c.cardID.equals("Necronomicurse") ||
//                    c.cardID.equals("CurseOfTheBell") ||
//                    c.cardID.equals("AscendersBane");
//
//            // 额外排除自定义标签
//            boolean isCantMove = c.hasTag(CantRemoved_TAG);
//            if (!isExcludedCurse && !isCantMove) {
//                retVal.addToTop(c);
//            }
//        }
//        for (int i = 0; i < retVal.size(); i++) {
//            System.out.println(retVal.group.get(i).name);
//        }
//        return retVal;
//    }
//}
