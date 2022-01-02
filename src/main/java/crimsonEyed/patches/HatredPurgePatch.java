package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import crimsonEyed.cards.basic.Hatred;

public class HatredPurgePatch {
    @SpirePatch(clz = CardGroup.class, method = "getPurgeableCards")
    public static class HatredLock {
        @SpireInsertPatch(loc=1096, localvars = {"retVal"})
        public static void Insert(CardGroup __group, CardGroup retVal) {
            for (AbstractCard c : retVal.group) {
                if (c.cardID == Hatred.ID)
                    __group.removeCard(c);
            }
        }
    }
}
