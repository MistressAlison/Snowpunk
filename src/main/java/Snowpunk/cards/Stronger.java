package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.StrongerPower;
import Snowpunk.powers.WinterStormPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Snowpunk.SnowpunkMod.makeID;

public class Stronger extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Stronger.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = -1, UP_COST = 3, MULTIPLIER = 2, UP_MULT = 1;

    public Stronger() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = MULTIPLIER;
        info = baseInfo = 0;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int snow = getSnow();
        if (snow > 0) {
            for (int i = 0 ; i < magicNumber ; i++) {
                Wiz.applyToSelf(new StrengthPower(p, snow));
            }
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeMagicNumber(UP_MULT), null, new int[]{}, true, new int[]{1, 2});
        addUpgradeData(this, () -> upgrade2(), null, new int[]{}, true, new int[]{0, 2});
        addUpgradeData(this, () -> upgrade3(), null, new int[]{}, true, new int[]{0, 1});
    }

    private void upgrade2() {
        upgradeInfo(1);
        SCostFieldPatches.SCostField.isSCost.set(this, false);
    }

    private void upgrade3() {
        upgradeBaseCost(UP_COST);
        SCostFieldPatches.SCostField.isSCost.set(this, false);
    }
}