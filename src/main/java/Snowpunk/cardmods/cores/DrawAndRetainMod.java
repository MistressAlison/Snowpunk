package Snowpunk.cardmods.cores;

import Snowpunk.cardmods.cores.effects.AbstractCardEffectMod;
import Snowpunk.cards.cores.AbstractCoreCard;
import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.cards.cores.util.OnUseCardInstance;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class DrawAndRetainMod extends AbstractCardEffectMod {
    public DrawAndRetainMod(String description, AbstractCoreCard.ValueType type, int effect, int upEffect, boolean secondVar) {
        super(description, type, effect, upEffect, secondVar);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseEffects(new OnUseCardInstance(priority, (p, m) -> {
                int amount = useSecondVar ? ((AssembledCard) card).secondMagic : card.magicNumber;
                Wiz.atb(new DrawCardAction(amount));
                Wiz.applyToSelf(new EquilibriumPower(p, 1));
            }));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawAndRetainMod(description, type, effect, upEffect, useSecondVar);
    }
}
