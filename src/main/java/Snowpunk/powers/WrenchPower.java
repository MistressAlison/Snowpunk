package Snowpunk.powers;

import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.powers.interfaces.OnClankPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class WrenchPower extends AbstractEasyPower implements OnClankPower, InvisiblePower, FreeToPlayPower {
    public static String POWER_ID = makeID(WrenchPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public WrenchPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        isTurnBased = true;
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        int getDraw = 0;
        if (owner.hasPower(FineTunePower.POWER_ID))
            getDraw = owner.getPower(FineTunePower.POWER_ID).amount;
        if (amount == 1) {
            description = DESCRIPTIONS[0] + getDraw + DESCRIPTIONS[getDraw > 1 ? 4 : 3];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + getDraw + DESCRIPTIONS[getDraw > 1 ? 4 : 3];
        }
    }

    @Override
    public void onClank(AbstractCard card) {
        flash();
        int getDraw = 0;
        if (owner.hasPower(FineTunePower.POWER_ID))
            getDraw = owner.getPower(FineTunePower.POWER_ID).amount;
        Wiz.atb(new DrawCardAction(getDraw));
    }


    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractPower makeCopy() {
        return new WrenchPower(owner, amount);
    }
}
