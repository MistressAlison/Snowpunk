package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.powers.SteamPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class BackupBoiler extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BackupBoiler.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 2;

    public BackupBoiler() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SteamPower(p, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeMagicNumber(1));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}