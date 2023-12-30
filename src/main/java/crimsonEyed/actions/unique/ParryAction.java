package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ParryAction extends AbstractGameAction {
    private AbstractMonster m;
    public ParryAction(int amt, AbstractMonster target) {
        this.actionType = ActionType.WAIT;
        this.amount = amt;
        this.m = target;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, amount, false)));
        }

        this.isDone = true;
    }
}
