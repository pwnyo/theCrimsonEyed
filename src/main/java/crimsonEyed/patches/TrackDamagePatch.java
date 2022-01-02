package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;

public class TrackDamagePatch {
    public static boolean tookDamageLastTurn;
    public static boolean tookDamageThisTurn;

    @SpirePatch(clz = AbstractRelic.class, method = "onLoseHp")
    public static class TookDamage {
        public static void Postfix(AbstractRelic __relic, int damageAmount) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
                tookDamageThisTurn = true;
            }
        }
    }
    @SpirePatch(clz = AbstractRelic.class, method = "atTurnStart")
    public static class NextTurn {
        public static void Postfix(AbstractRelic __relic) {
            tookDamageLastTurn = tookDamageThisTurn;
            tookDamageThisTurn = false;
            SasukeMod.logger.info("damaged this turn? " + tookDamageThisTurn + " damaged last turn? " + tookDamageLastTurn);
        }
    }
    @SpirePatch(clz = AbstractRelic.class, method = "atBattleStart")
    public static class StartBattle {
        public static void Postfix(AbstractRelic __relic) {
            tookDamageLastTurn = false;
            tookDamageThisTurn = false;
        }
    }
}
