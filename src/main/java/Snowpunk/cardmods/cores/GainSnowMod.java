package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GainSnowMod extends AbstractCardEffectMod {
    public GainSnowMod(String description, boolean secondVar) {
        super(description, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.applyToSelf(new SnowballPower(p, amount));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainSnowMod(description, useSecondVar);
    }
}
