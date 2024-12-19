package crimsonEyed.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;

public class BlazeBarrierAction extends AbstractGameAction {
    boolean upgraded;
    public BlazeBarrierAction(boolean upgraded) {
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (upgraded) {
                addToTop(new TriggerPassiveAction(1));
            }
            addToTop(new WaitAction(0.1f));
            addToTop(new ChannelAction(new Dark()));
            this.isDone = true;
        }
    }
}
