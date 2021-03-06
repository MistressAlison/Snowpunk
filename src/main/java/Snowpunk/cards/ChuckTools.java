package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.powers.TinkerNextCardPower;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static Snowpunk.SnowpunkMod.makeID;

public class ChuckTools extends AbstractEasyCard {
    public final static String ID = makeID(ChuckTools.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;
    private static final int TINKER = 1;
    private static final int UP_TINKER = 1;

    public ChuckTools() {
        super(ID, COST, TYPE, RARITY, TARGET);
        secondMagic = baseSecondMagic = EFFECT;
        magicNumber = baseMagicNumber = TINKER;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                Wiz.applyToEnemy(mo, new VulnerablePower(mo, secondMagic, false));
                //Wiz.applyToEnemy(mo, new WeakPower(mo, secondMagic, false));
            }
        }
        Wiz.applyToSelf(new TinkerNextCardPower(p, magicNumber));
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        upgradeMagicNumber(UP_TINKER);
    }
}