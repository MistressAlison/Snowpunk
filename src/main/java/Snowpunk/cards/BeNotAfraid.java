package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.CarolingPower;
import Snowpunk.powers.GracePower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BeNotAfraid extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BeNotAfraid.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;

    public BeNotAfraid() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        secondMagic = baseSecondMagic = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new GracePower(p, magicNumber, secondMagic));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(1));
        addUpgradeData(() -> upgradeSecondMagic(4));
        addUpgradeData(() -> upgradeSecondMagic(-4));
        addUpgradeData(() -> upgradeBaseCost(2));
        setExclusions(1, 2);
    }
}