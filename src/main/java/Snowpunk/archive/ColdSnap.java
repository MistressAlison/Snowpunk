package Snowpunk.archive;

import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.SCostFieldPatches;
import Snowpunk.powers.FrostbitePower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ColdSnap extends AbstractEasyCard {
    public final static String ID = makeID(ColdSnap.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 4;
    private static final int UP_DMG = 2;

    public ColdSnap() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        this.exhaust = true;
        SCostFieldPatches.SCostField.isSCost.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getSnow() > 0) {
            for (int i = 0 ; i < multiDamage.length ; i++) {
                multiDamage[i] *= getSnow();
            }
            Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new FrostbitePower(mo, p, getSnow()));
                }
            }
        }
        if (!this.freeToPlayOnce) {
            //Wiz.atb(new RemoveSpecificPowerAction(p, p, SnowballPower.POWER_ID));
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}