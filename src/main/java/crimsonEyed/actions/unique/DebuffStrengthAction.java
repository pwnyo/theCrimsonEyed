package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class DebuffStrengthAction extends AbstractGameAction {
    public DebuffStrengthAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    public void update() {
        if (this.target != null && (this.target.hasPower(WeakPower.POWER_ID) || this.target.hasPower(VulnerablePower.POWER_ID))) {
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, amount)));
        }
        this.isDone = true;
    }
}
