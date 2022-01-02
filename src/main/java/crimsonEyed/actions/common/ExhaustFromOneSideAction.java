package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustFromOneSideAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractPlayer p;
    private boolean fromLeft;

    public ExhaustFromOneSideAction(boolean fromLeft) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.EXHAUST;
        this.duration = 0.01F;
        this.amount = 1;
        this.fromLeft = fromLeft;
    }public ExhaustFromOneSideAction(boolean fromLeft, int amount) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.EXHAUST;
        this.duration = 0.01F;
        this.amount = amount;
        this.fromLeft = fromLeft;
    }

    public void update() {
        if (this.p == null || p.hand.size() <= 0) {
            this.isDone = true;
            return;
        }
        else {
            int count = p.hand.size();

            for (int i = 0; i < count; ++i) {
                if (fromLeft)
                    this.p.hand.moveToExhaustPile(this.p.hand.getTopCard());
                else
                    this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                if (i >= amount - 1) {
                    break;
                }
            }

            isDone = true;
        }
    }
}
