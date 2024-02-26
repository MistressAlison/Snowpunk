package Snowpunk.cards;

import Snowpunk.actions.ClankAction;
import Snowpunk.actions.EvaporateCardInHandAction;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.abstracts.ClankCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FullSteamAheadPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class FullSteamAhead extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FullSteamAhead.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 4;

    public FullSteamAhead() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        isEthereal = true;
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        addToBot(new VFXAction(player, new InflameEffect(player), .5F));
        Wiz.applyToSelf(new FullSteamAheadPower(player, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            isEthereal = false;
            uDesc();
        });
    }
}