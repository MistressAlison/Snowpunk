package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.DoublePlayOverheatMod;
import Snowpunk.cardmods.parts.SetCostMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class EmberForge extends AbstractPartCard {
    public static final String ID = makeID(EmberForge.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;

    public EmberForge() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return greaterThanZeroCost;
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new SetCostMod(0));
    }
}
