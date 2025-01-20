package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;

public class TomoeAction extends AbstractGameAction {
    AbstractPlayer p;

    public TomoeAction(int gain) {
        p = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
        this.amount = gain;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasEmptyOrb()) {
            addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1)));
        }
        this.isDone = true;
    }
}
