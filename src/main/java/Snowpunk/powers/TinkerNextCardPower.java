package Snowpunk.powers;

import Snowpunk.actions.TinkerAction;
import Snowpunk.patches.CustomTags;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static Snowpunk.SnowpunkMod.makeID;

public class TinkerNextCardPower extends AbstractEasyPower {
    public static String POWER_ID = makeID(TinkerNextCardPower.class.getSimpleName());
    public static PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = strings.DESCRIPTIONS;

    private boolean justApplied;

    public TinkerNextCardPower(AbstractCreature owner, int amount) {
        this(owner, amount, true);
    }

    public TinkerNextCardPower(AbstractCreature owner, int amount, boolean appliedByCard) {
        super(POWER_ID, strings.NAME, PowerType.BUFF, false, owner, amount);
        this.loadRegion("curiosity");
       // justApplied = appliedByCard;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse) {
            flash();
            TinkerAction.tinkerCard(card);
            if (!card.hasTag(CustomTags.MENDING)) {
                card.purgeOnUse = true;
            }

            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }

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
