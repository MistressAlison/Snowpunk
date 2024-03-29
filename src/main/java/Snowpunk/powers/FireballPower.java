package Snowpunk.powers;

import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.interfaces.FreeToPlayPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class FireballPower extends AbstractEasyPower implements FreeToPlayPower {
    public static String POWER_ID = makeID(FireballPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    public FireballPower(AbstractCreature owner, int amount) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("attackBurn");
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && !owner.hasPower(OverheatNextCardPower.POWER_ID) && !owner.hasPower(SteamPower.POWER_ID) &&
                CardTemperatureFields.canModTemp(card, 1)) {
            flash();
            CardTemperatureFields.addHeat(card, 1);
            addToTop(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return CardTemperatureFields.getCardHeat(card) == CardTemperatureFields.HOT && Wiz.adp().hand.contains(card);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}
