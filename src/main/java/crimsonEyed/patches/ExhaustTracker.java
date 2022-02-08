package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ExhaustTracker {
    public static int totalCardsExhaustedThisTurn;

    @SpirePatch(clz = AbstractRelic.class, method = "atTurnStart")
    public static class NextTurn {
        public static void Postfix(AbstractRelic __relic) {
            totalCardsExhaustedThisTurn = 0;
        }
    }
    @SpirePatch(clz = AbstractRelic.class, method = "atBattleStart")
    public static class StartBattle {
        public static void Postfix(AbstractRelic __relic) {
            totalCardsExhaustedThisTurn = 0;
        }
    }
    @SpirePatch(clz = AbstractCard.class, method = "triggerOnExhaust")
    public static class TookDamage {
        public static void Postfix(AbstractCard __card) {
            totalCardsExhaustedThisTurn++;
        }
    }
}