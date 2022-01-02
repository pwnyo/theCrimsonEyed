package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.patches.ExhaustTracker;

public class KirinAction extends AbstractGameAction {
    private boolean upgraded;
    public KirinAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }// 16

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && !AbstractDungeon.player.orbs.isEmpty()) {
            for (int i = 0; i < ExhaustTracker.totalCardsExhaustedThisTurn; i++) {
                addToBot(new ChannelAction(new Lightning()));
            }
            this.addToBot(new EvokeAllOrbsAction());
        }
        this.isDone = true;
    }
}
