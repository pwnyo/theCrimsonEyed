package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SeverThePastBlockAction extends AbstractGameAction {
    public SeverThePastBlockAction(int amount) {
        this.actionType = ActionType.BLOCK;
        this.amount = amount;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.exhaustPile.size() > 0) {
            addToBot(new GainBlockAction(p, amount * p.exhaustPile.size()));
        }
        this.isDone = true;
    }
}
