package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crimsonEyed.SasukeMod.makeID;

public class TsukuyomiAction extends AbstractGameAction {
    AbstractMonster target;
    public TsukuyomiAction(AbstractMonster m, int amount) {
        this.actionType = ActionType.DAMAGE;
        this.amount = amount;
        target = m;
    }

    public void update() {
        int debuffs = 0;
        for (AbstractPower pow : target.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled"))
                debuffs++;
        }
        if (debuffs > 0) {
            addToBot(new SFXAction(makeID("MANGEKYOU")));
            addToBot(new VFXAction(new TsukuyomiCurseEffect(target.hb.cX, target.hb.cY, debuffs), Math.min(0.2F, 0.05F * debuffs)));
            addToBot(new WaitAction(0.1F));
            addToBot(new LoseHPAction(target, AbstractDungeon.player, debuffs * amount));
        }
        isDone = true;
    }
}
