package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LockOnPower;


public class SureFireAction extends AbstractGameAction {
    private DamageInfo info;

    public SureFireAction(AbstractCreature target, DamageInfo info) {
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.info = info;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(LockOnPower.POWER_ID)) {
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            this.addToTop(new GainEnergyAction(1));
        }

        this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
        this.isDone = true;
    }
}
