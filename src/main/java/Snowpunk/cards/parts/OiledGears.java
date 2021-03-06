package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.DrawAndDiscardRandomMod;
import Snowpunk.cardmods.parts.DrawMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class OiledGears extends AbstractPartCard {
    public static final String ID = makeID(OiledGears.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;

    public OiledGears() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(card -> !CardModifierManager.hasModifier(card, DrawMod.ID));
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new DrawMod(1));
    }
}
