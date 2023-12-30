package crimsonEyed.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;

public class BlazeBarrierAction extends AbstractGameAction {
    public BlazeBarrierAction(int amount) {
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.orbs.isEmpty() && (p.orbs.get(0) instanceof Dark)) {
                addToBot(new TriggerPassiveAction(amount));
            }
            else {
                addToBot(new ChannelAction(new Dark()));
            }
            this.isDone = true;
        }
    }
}
