package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PaybackAction extends AbstractGameAction {
    public PaybackAction(AbstractMonster target) {
        this.actionType = ActionType.ENERGY;
        this.target = target;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int count = 0;
        for (AbstractPower pow : target.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled")) {
                count++;
            }
        }
        if (count > 0) {
            addToBot(new GainEnergyAction(count));
        }
        this.isDone = true;
    }
}
