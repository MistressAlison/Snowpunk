package Snowpunk.actions;

import Snowpunk.SnowpunkMod;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

import static Snowpunk.SnowpunkMod.makeID;

public class TinkerAction extends AbstractGameAction {
    public static final String ID = makeID("Tinker");
    public static String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    AbstractCard card;

    public TinkerAction(AbstractCard card) {
        this.card = card;
    }

    public TinkerAction() {
        this(null);
    }

    @Override
    public void update() {
        //If we have a pre-specified card, heat it
        if (card != null) {
            pickPartsForCard(card);
        } else {
            if (Wiz.adp().hand.group.stream().anyMatch(TinkerAction::acceptsAPart)) {
                HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
                ArrayList<AbstractCard> selectionGroup = new ArrayList<>();
                for (AbstractCard c : Wiz.adp().hand.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    cardMap.put(copy, c);
                    selectionGroup.add(copy);
                }

                Wiz.att(new SelectCardsAction(selectionGroup, 1, TEXT[0], false, TinkerAction::acceptsAPart, cards -> {
                    for (AbstractCard c : cards) {
                        pickPartsForCard(cardMap.get(c));
                    }
                }));
            }
        }
        this.isDone = true;
    }

    //TODO - Lifted directly, needs alteration
    private static AbstractCard.CardRarity rollRarity() {
        int roll = AbstractDungeon.cardRng.random(99);
        int rareRate = 3;
        if (roll < rareRate) {
            return AbstractCard.CardRarity.RARE;
        } else {
            return roll < 40 ? AbstractCard.CardRarity.UNCOMMON : AbstractCard.CardRarity.COMMON;
        }
    }

    private static void pickPartsForCard(AbstractCard card) {
        CardGroup validParts = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractPartCard pc : SnowpunkMod.parts) {
            if (pc.getFilter().test(card)) {
                validParts.addToTop(pc.makeCopy());
            }
        }
        if (!validParts.isEmpty()) {
            ArrayList<AbstractCard> cardsToPick = new ArrayList<>();
            if (validParts.size() <= 3) {
                cardsToPick.addAll(validParts.group);
            } else {
                for (int i = 0 ; i < 3 ; i++) {
                    AbstractCard c = validParts.getRandomCard(true);
                    validParts.removeCard(c);
                    cardsToPick.add(c);
                }
            }
            Wiz.att(new SelectCardsCenteredAction(cardsToPick, 1, TEXT[1]+card.name+TEXT[2], false, crd -> true, cards -> {
                for (AbstractCard c : cards) {
                    if (c instanceof AbstractPartCard) {
                        ((AbstractPartCard) c).apply(card);
                    }
                }
            }));
        }
    }

    private static boolean acceptsAPart(AbstractCard card) {
        return SnowpunkMod.parts.stream().anyMatch(part -> part.getFilter().test(card));
    }
}