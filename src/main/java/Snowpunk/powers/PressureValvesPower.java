package Snowpunk.powers;

import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.interfaces.SnowAmountModifier;
import basemod.interfaces.XCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class PressureValvesPower extends AbstractEasyPower implements XCostModifier, SnowAmountModifier {
    public static String POWER_ID = makeID(PressureValvesPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public PressureValvesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("panache");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int modifyX(AbstractCard abstractCard) {
        flash();
        return amount;
    }

    @Override
    public boolean xCostModifierActive(AbstractCard c) {
        return !SCostFieldPatches.SCostField.isSCost.get(c);
    }

    @Override
    public int modifySnow() {
        return amount;
    }
}
