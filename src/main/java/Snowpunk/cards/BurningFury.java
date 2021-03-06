package Snowpunk.cards;

import Snowpunk.cardmods.parts.ReshuffleMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BurningFury extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BurningFury.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, UP_COST = 0, FIRE = 2, UP_FIRE = 1, RESHUFFLE = 1;

    public BurningFury() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = FIRE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Fireball(), magicNumber, false));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST));
        addUpgradeData(this, () -> upgradeMagicNumber(UP_FIRE));
        addUpgradeData(this, () -> CardModifierManager.addModifier(this, new ReshuffleMod()));
    }
}