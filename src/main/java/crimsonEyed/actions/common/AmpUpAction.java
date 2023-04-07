package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

public class AmpUpAction extends AbstractGameAction {
    public AmpUpAction() {
        this.actionType = ActionType.ENERGY;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int count = 0;

            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof Lightning) {
                    count++;
                }
            }

            if (count > 0) {
                addToBot(new GainEnergyAction(count));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
