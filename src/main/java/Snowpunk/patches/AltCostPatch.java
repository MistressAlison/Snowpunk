package Snowpunk.patches;

import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.ModifyXCostPatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;

public class AltCostPatch {
    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class AltCostField {
        public static SpireField<Boolean> usingAltCost = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = ModifyXCostPatch.class, method = "Insert")
    public static class CheckAltCost {
        static boolean wasFreeToPlay;

        @SpirePrefixPatch
        public static void reset(AbstractCard c) {
            AltCostField.usingAltCost.set(c, false);
            wasFreeToPlay = c.freeToPlayOnce;
        }

        @SpireInsertPatch(locator = Locator.class)
        public static void check(AbstractCard c) {
            if (!wasFreeToPlay && c.freeToPlayOnce) {
                AltCostField.usingAltCost.set(c, true);
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(CardGroup.class, "group");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
