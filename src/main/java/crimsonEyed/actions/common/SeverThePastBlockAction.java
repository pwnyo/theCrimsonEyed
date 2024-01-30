package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SeverThePastBlockAction extends AbstractGameAction {
    public SeverThePastBlockAction(int block) {
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.amount = block;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.exhaustPile.size() > 0) {
            addToBot(new GainBlockAction(p, amount * p.exhaustPile.size()));
        }
        this.isDone = true;
    }
}
