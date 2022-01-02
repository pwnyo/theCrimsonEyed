package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import crimsonEyed.patches.interfaces.IOnMonsterDeathListenerPower;

@SpirePatch(clz= AbstractMonster.class, method = "die", paramtypez = {})
public class OnMonsterDeathPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractMonster __m) {
        if (__m.currentHealth <= 0 && !__m.hasPower(MinionPower.POWER_ID)) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof IOnMonsterDeathListenerPower) {
                    ((IOnMonsterDeathListenerPower) p).onMonsterDeath(__m);
                }
            }
        }
    }
}
