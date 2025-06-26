package crimsonEyed.actions.common;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.core.Settings;

public class WaitTriggerPassiveAction extends AbstractGameAction {
    boolean triggerAll = false;
    public WaitTriggerPassiveAction(int amount) {
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = amount;
    }
    public WaitTriggerPassiveAction(int amount, boolean triggerAll) {
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = amount;
        this.triggerAll = triggerAll;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (!triggerAll) {
                addToTop(new TriggerPassiveAction(amount));
            }
            else {
                for (int i = 0; i < amount; i++) {
                    addToTop(new ImpulseAction());
                }
            }
            this.isDone = true;
        }

        this.tickDuration();
    }
}