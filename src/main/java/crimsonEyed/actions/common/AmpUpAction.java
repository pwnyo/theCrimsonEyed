package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmpUpAction extends AbstractGameAction {
    public AmpUpAction() {
        this.actionType = ActionType.ENERGY;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            int count = AbstractDungeon.player.filledOrbCount();
            if (count > 0) {
                addToBot(new GainEnergyAction(count));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
