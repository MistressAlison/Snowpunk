package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.cards.interfaces.OnTinkeredCard;
import Snowpunk.cards.parts.AbstractPartCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BurnPower;
import Snowpunk.powers.ChillPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class BrassKnuckles extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BrassKnuckles.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int DMG = 5;
    private static final int UP_DMG = 2;
    private static final int DOWN_DMG = -1;
    private static final int BURN = 3;
    private static final int CHILL = 2;
    private static final int BONUS = 1;

    private boolean third = false;
    private boolean burn = false;
    private boolean chill = false;

    public BrassKnuckles() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (third) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
        if (burn) {
            Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        }
        if (chill) {
            Wiz.applyToEnemy(m, new ChillPower(m, magicNumber));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(this, () -> {
            CardTemperatureFields.addInherentHeat(this, 1);
            baseMagicNumber = magicNumber = 0;
            burn = true;
            upgradeMagicNumber(BURN);
            name = cardStrings.EXTENDED_DESCRIPTION[3];
            initializeTitle();
            uDesc();
        }, new int[]{}, new int[]{1});
        addUpgradeData(this, () -> {
            CardTemperatureFields.addInherentHeat(this, -1);
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
            baseMagicNumber = magicNumber = 0;
            chill = true;
            name = cardStrings.EXTENDED_DESCRIPTION[4];
            initializeTitle();
            upgradeMagicNumber(CHILL);
            initializeDescription();
        }, new int[]{}, new int[]{0});
        addUpgradeData(this, () -> upgradeDamage(UP_DMG), 0);
        addUpgradeData(this, () -> upgradeBaseCost(UP_COST), 1);
        addUpgradeData(this, () -> {
            third = true;
            upgradeDamage(DOWN_DMG);
            upgradeMagicNumber(BONUS);
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }, 2);
        addUpgradeData(this, () -> {
            third = true;
            upgradeDamage(DOWN_DMG);
            upgradeMagicNumber(BONUS);
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[2];
            initializeDescription();
        },  3);
    }
}