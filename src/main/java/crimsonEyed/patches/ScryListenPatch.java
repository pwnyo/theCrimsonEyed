package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crimsonEyed.actions.unique.ScryBlockAction;

@SpirePatch(clz = ScryAction.class, method = "update")
public class ScryListenPatch {
    public static boolean scriedThisTurn;

    @SpirePostfixPatch
    public static void Postfix(ScryAction __action) {
        scriedThisTurn = true;
    }
    @SpirePatch(clz = AbstractRelic.class, method = "atTurnStart")
    public static class NextTurn {
        public static void Postfix(AbstractRelic __relic) {
            scriedThisTurn = false;
        }
    }
    @SpirePatch(clz = AbstractRelic.class, method = "atBattleStart")
    public static class StartBattle {
        public static void Postfix(AbstractRelic __relic) {
            scriedThisTurn = false;
        }
    }
}
