package Snowpunk.cards;

import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.LinkMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.HashMap;

import static Snowpunk.SnowpunkMod.makeID;

public class ChuggaChugga extends AbstractEasyCard {
    public final static String ID = makeID(ChuggaChugga.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;

    public ChuggaChugga() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardTemperatureFields.addInherentHeat(this, 1);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        HashMap<AbstractCard, AbstractCard> cardMap = new HashMap<>();
        ArrayList<AbstractCard> selectionGroup = new ArrayList<>(), selectedCards = new ArrayList<>(), linkedCards = new ArrayList<>();

        for (AbstractCard c : p.hand.group) {
            AbstractCard copy = c.makeStatEquivalentCopy();
            cardMap.put(copy, c);
            selectionGroup.add(copy);
        }
        if (selectionGroup.size() <= 1)
            return;
        Wiz.att(new BetterSelectCardsCenteredAction(selectionGroup, 2, "", false, card -> true, cards -> {
            for (AbstractCard c : cards) {
                selectedCards.add(cardMap.get(c));
            }
            for (AbstractCard c : cards) {
//                CardModifierManager.addModifier(cardMap.get(c), new LinkMod(selectedCards, cardMap.get(c)));
                LinkMod.Link(selectedCards, cardMap.get(c));
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(1);
        upgradedCost = true;
    }
}