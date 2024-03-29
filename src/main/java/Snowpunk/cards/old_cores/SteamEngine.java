package Snowpunk.cards.old_cores;

import Snowpunk.cardmods.BetterExhaustMod;
import Snowpunk.cardmods.cores.GainSteamMod;
import Snowpunk.cardmods.cores.edits.CardEditMod;
import Snowpunk.util.Triplet;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class SteamEngine extends AbstractCoreCard {
    public static final String ID = makeID(SteamEngine.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final EffectTag VALUE = EffectTag.MAGIC;

    private static final int COST = 1;
    private static final int EFFECT = 1;
    public static final int UP_EFFECT = 1;

    public SteamEngine() {
        super(ID, COST, TYPE, EffectTag.MAGIC);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = EFFECT;
        CardModifierManager.addModifier(this, new BetterExhaustMod());
    }

    @Override
    public void apply(AbstractCard card) {
        CardModifierManager.addModifier(card, new CardEditMod(TEXT[0], COST, TYPE, CardRarity.SPECIAL, TARGET));
        CardModifierManager.addModifier(card, new GainSteamMod(rawDescription, useSecondMagic));
        CardModifierManager.addModifier(card, new BetterExhaustMod());
        if (card instanceof ARCHIVED_AssembledCard) {
            ((ARCHIVED_AssembledCard) card).addInfo(new Triplet<>(ARCHIVED_AssembledCard.SaveInfo.CoreType.STEAM_ENGINE, useSecondMagic, UP_EFFECT));
            ((ARCHIVED_AssembledCard) card).saveMagic(EFFECT, useSecondMagic);
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(UP_EFFECT);
    }
}
