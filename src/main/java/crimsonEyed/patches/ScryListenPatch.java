package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ScryListenPatch {
    public static boolean scriedThisTurn;

    @SpirePatch(clz = ScryAction.class, method = "update")
    public static class OnScry {
        @SpirePostfixPatch
        public static void Postfix(ScryAction __action) {
            scriedThisTurn = true;
        }
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
    @SpirePatch(clz = ScryAction.class, method = "update")
    public static class OnScryDiscard {
        @SpireInsertPatch(loc = 64)
        public static void Insert(ScryAction __action) {
            for (AbstractCard c: AbstractDungeon.gridSelectScreen.selectedCards) {
                if (c instanceof IScryDiscardListener) {
                    ((IScryDiscardListener) c).onScryDiscard();
                }
            }
        }
    }
    public interface IScryDiscardListener {
        void onScryDiscard();
    }
}
