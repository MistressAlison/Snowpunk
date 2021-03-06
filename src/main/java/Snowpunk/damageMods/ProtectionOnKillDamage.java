package Snowpunk.damageMods;

import Snowpunk.powers.ProtectionPower;
import Snowpunk.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ProtectionOnKillDamage extends AbstractDamageModifier {

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature targetHit) {
        if (targetHit.currentHealth > 0 && targetHit.currentHealth - lastDamageTaken <= 0 && !targetHit.halfDead) {
            Wiz.applyToSelf(new ProtectionPower(Wiz.adp(), 1));
        }
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new ProtectionOnKillDamage();
    }
}
