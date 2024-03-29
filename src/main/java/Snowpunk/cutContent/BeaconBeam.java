package Snowpunk.cutContent;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.BeaconBeamPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BeaconBeam extends AbstractEasyCard {
    public final static String ID = makeID(BeaconBeam.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int STACKS = 1;

    public BeaconBeam() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = STACKS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BeaconBeamPower(p, magicNumber));
    }

    public void upp() {
        isInnate = true;
        uDesc();
    }
}