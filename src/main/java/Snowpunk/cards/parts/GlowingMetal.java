package Snowpunk.cards.parts;

import Snowpunk.cardmods.parts.BurnDamageMod;
import Snowpunk.cardmods.parts.CoolOnDrawMod;
import Snowpunk.cardmods.parts.HeatOnDrawMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class GlowingMetal extends AbstractPartCard {
    public static final String ID = makeID(GlowingMetal.class.getSimpleName());

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    public GlowingMetal() {
        super(ID, TYPE, RARITY);
    }

    @Override
    public Predicate<AbstractCard> getFilter() {
        return isPlayable.and(isAttack).and(card -> !CardModifierManager.hasModifier(card, BurnDamageMod.ID));
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new BurnDamageMod(3));
    }
}
