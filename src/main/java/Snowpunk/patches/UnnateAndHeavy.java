package Snowpunk.patches;

import Snowpunk.cards.AnotherAnvil;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class UnnateAndHeavy {
    @SpirePatch(clz = CardGroup.class, method = "initializeDeck")
    public static class UnnateDeckInitializePatch {
        @SpireInsertPatch(
                rloc = 6,
                localvars = {"copy"}
        )
        public static void initializeDeckPatch(CardGroup combatDeck, @ByRef CardGroup[] copy) {
            ArrayList<AbstractCard> placeOnBot = new ArrayList();
            Iterator vacantIterator = copy[0].group.iterator();

            while (vacantIterator.hasNext()) {
                AbstractCard newCard = (AbstractCard) vacantIterator.next();
                if (newCard instanceof AnotherAnvil) {
                    placeOnBot.add(newCard);
                }
            }
            if (placeOnBot.size() > 0) {
                for (AbstractCard abstractCard : placeOnBot) {
                    copy[0].removeCard(abstractCard);
                    copy[0].addToBottom(abstractCard);
                }
            }
        }
    }

    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class HeavyReshuffle {
        @SpireInsertPatch(
                rloc = 3
        )
        public static void update() {
            ArrayList<AbstractCard> placeOnBot = new ArrayList();
            CardGroup deck = AbstractDungeon.player.discardPile;
            Iterator vacantIterator = deck.group.iterator();

            while (vacantIterator.hasNext()) {
                AbstractCard newCard = (AbstractCard) vacantIterator.next();
                if (newCard instanceof AnotherAnvil)
                    placeOnBot.add(newCard);
            }
            if (placeOnBot.size() > 0) {
                for (AbstractCard abstractCard : placeOnBot) {
                    deck.removeCard(abstractCard);
                    deck.addToBottom(abstractCard);
                }
            }
        }
    }
}