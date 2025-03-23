package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class ChidoriAction extends AbstractGameAction {
    public ChidoriAction(AbstractCreature target) {
        this.target = target;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(LockOnPower.POWER_ID)) {
            addToTop(new EvokeOrbAction(1));
        }
        this.isDone = true;
    }
}
