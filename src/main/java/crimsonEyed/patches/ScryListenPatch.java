package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crimsonEyed.patches.interfaces.IScryListenerRelic;

@SpirePatch(clz = ScryAction.class, method = "update")
public class ScryListenPatch {
    @SpireInsertPatch(loc = 43)
    public static void Insert(ScryAction __action) {
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof IScryListenerRelic) {
                IScryListenerRelic listener = (IScryListenerRelic) r;
                listener.onScry();
            }
        }
    }
}
