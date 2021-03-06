package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.GainEnergyMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class Battery extends AbstractPartCard {
    public static final String ID = makeID(Battery.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;

    public Battery() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(card -> !CardModifierManager.hasModifier(card, GainEnergyMod.ID) || card.cost >= ((GainEnergyMod) CardModifierManager.getModifiers(card, GainEnergyMod.ID).get(0)).amount);
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new GainEnergyMod(1));
    }
}
