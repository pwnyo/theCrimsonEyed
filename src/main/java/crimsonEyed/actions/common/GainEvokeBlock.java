package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class GainEvokeBlock extends AbstractGameAction {
    public GainEvokeBlock() {
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            AbstractPlayer p = AbstractDungeon.player;

            if (p.orbs.isEmpty() || p.orbs.get(0) instanceof EmptyOrbSlot) {
                isDone = true;
                return;
            }
            addToBot(new GainBlockAction(p, p.orbs.get(0).evokeAmount));
        }

        this.tickDuration();
    }
}
