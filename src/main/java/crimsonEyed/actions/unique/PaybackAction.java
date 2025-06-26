package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class PaybackAction extends AbstractGameAction {
    public PaybackAction(AbstractMonster target, int energy) {
        this.actionType = ActionType.ENERGY;
        this.target = target;
        this.amount = energy;
    }

    public void update() {
        if (this.target != null && (this.target.hasPower(WeakPower.POWER_ID) || this.target.hasPower(VulnerablePower.POWER_ID))) {
            addToTop(new GainEnergyAction(amount));
        }
        this.isDone = true;
    }
}
