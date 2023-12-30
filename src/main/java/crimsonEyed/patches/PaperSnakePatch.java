package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.LockOnPower;
import crimsonEyed.relics.commoner.PaperSnek;

import java.util.ArrayList;

public class PaperSnakePatch {
    @SpirePatch(clz = AbstractOrb.class, method = "applyLockOn")
    public static class SingleLockOn {
        public static SpireReturn<Integer> Prefix(AbstractCreature target, int dmg) {
            if (target.hasPower(LockOnPower.POWER_ID) && AbstractDungeon.player.hasRelic(PaperSnek.ID)) {
                return SpireReturn.Return((int)(dmg * 1.75F));
            }
            else {
                return SpireReturn.Continue();
            }
        }
    }
    @SpirePatch(clz = DamageInfo.class, method = "createDamageMatrix",
            paramtypez = {int.class, boolean.class, boolean.class   })
    public static class MultiLockOn {
        public static int[] Postfix(int[] __result, int baseDamage, boolean isPureDamage, boolean isOrbDamage) {
            boolean hasSnake = AbstractDungeon.player.hasRelic(PaperSnek.ID);
            if (!hasSnake) {
                return __result;
            }
            ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
            int[] retVal = new int[monsters.size()];

            for (int i = 0; i < retVal.length; ++i) {
                DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);
                if (isOrbDamage && (monsters.get(i).hasPower(LockOnPower.POWER_ID))) {
                    info.output = (int)((float)info.base * 1.75F);
                }

                retVal[i] = info.output;
            }
            return retVal;
        }
    }
    @SpirePatch(clz = LockOnPower.class, method = "updateDescription")
    public static class ChangeLockOnDesc {
        public static void Postfix(LockOnPower __power) {
            if (__power.owner != null && AbstractDungeon.player.hasRelic(PaperSnek.ID)) {
                if (__power.amount == 1) {
                    __power.description = LockOnPower.DESCRIPTIONS[0] + 75 + LockOnPower.DESCRIPTIONS[1] + __power.amount + LockOnPower.DESCRIPTIONS[2];
                } else {
                    __power.description = LockOnPower.DESCRIPTIONS[0] + 75 + LockOnPower.DESCRIPTIONS[1] + __power.amount + LockOnPower.DESCRIPTIONS[3];
                }
            }
        }
    }
}
