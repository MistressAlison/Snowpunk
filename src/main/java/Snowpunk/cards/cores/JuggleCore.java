package Snowpunk.cards.cores;

import Snowpunk.cardmods.cores.*;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class JuggleCore extends AbstractCoreCard {
    public static final String ID = makeID(JuggleCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final ValueType VALUE = ValueType.MAGIC;

    private static final int SKIM = 3;
    private static final int UP_SKIM = 1;
    private static final int DRAW_RET = 2;
    private static final int UP_DRAW_RET = 1;
    private static final int DOPPEL = 2;
    private static final int UP_DOPPEL = 1;
    private static final int DRAW_TO = 6;
    private static final int UP_DRAW_TO = 1;

    String nameToAdd;
    int effectIndex;
    int effects = 4;

    public JuggleCore() {
        super(ID, TYPE, RARITY, VALUE);
        baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = SKIM;
    }

    @Override
    public void apply(AbstractCard card) {
        switch (effectIndex) {
            case 0:
                CardModifierManager.addModifier(card, new DrawCardMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, SKIM, UP_SKIM));
                break;
            case 1:
                CardModifierManager.addModifier(card, new DrawRetMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, DRAW_RET, UP_DRAW_RET));
                break;
            case 2:
                CardModifierManager.addModifier(card, new DoppelMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, DOPPEL, UP_DOPPEL));
                break;
            case 3:
                CardModifierManager.addModifier(card, new DrawUntilMod(nameToAdd, rawDescription, TYPE, RARITY, TARGET, DRAW_TO, UP_DRAW_TO));
                break;
            default:
        }
    }

    @Override
    public void prepForSelection(ArrayList<AbstractCoreCard> chosenCores) {
        effectIndex = AbstractDungeon.cardRandomRng.random(effects-1); // Inclusive RNG call needs a -1
        nameToAdd = TEXT[effectIndex*2];
        rawDescription = TEXT[effectIndex*2+1];
        initializeDescription();
        if (chosenCores.stream().anyMatch(c -> c.valueType == VALUE)) {
            swapDynvarKey(VALUE);
        }
        if (effectIndex == 0) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = SKIM;
        } else if (effectIndex == 1) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = DRAW_RET;
        } else if (effectIndex == 2) {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = DOPPEL;
        } else {
            baseMagicNumber = magicNumber = secondMagic = baseSecondMagic = DRAW_TO;
        }
    }

    @Override
    public void upp() {
        if (effectIndex == 0) {
            upgradeMagicNumber(UP_SKIM);
            upgradeSecondMagic(UP_SKIM);
        } else if (effectIndex == 1) {
            upgradeMagicNumber(UP_DRAW_RET);
            upgradeSecondMagic(UP_DRAW_RET);
        } else if (effectIndex == 2) {
            upgradeMagicNumber(UP_DOPPEL);
            upgradeSecondMagic(UP_DOPPEL);
        } else {
            upgradeMagicNumber(UP_DRAW_TO);
            upgradeSecondMagic(UP_DRAW_TO);
        }
    }
}
