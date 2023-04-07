package crimsonEyed.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class EvokeNextOrbOfTypeAction extends AbstractGameAction {
    AbstractOrb orbType;
    public EvokeNextOrbOfTypeAction(AbstractOrb orbType) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = orbType;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty()) {
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o != null && o.ID != null && o.ID.equals(orbType.ID)) {
                    addToBot(new EvokeSpecificOrbAction(o));
                    break;
                }
            }
        }

        this.isDone = true;
    }
}
