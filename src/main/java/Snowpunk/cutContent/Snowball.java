package Snowpunk.cutContent;

import Snowpunk.actions.GainSnowballAction;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class Snowball extends AbstractEasyCard {
    public final static String ID = makeID(Snowball.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int SNOW = 1;

    public Snowball() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SNOW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainSnowballAction((magicNumber)));
    }

    public void upp() {
        CardTemperatureFields.addInherentHeat(this, -1);
    }
}