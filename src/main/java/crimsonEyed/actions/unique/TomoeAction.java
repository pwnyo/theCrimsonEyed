package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;

public class TomoeAction extends AbstractGameAction {
    AbstractPlayer p;
    int requiredOrbs;
    int gain;

    public TomoeAction(int gain, int req) {
        p = AbstractDungeon.player;
        this.actionType = ActionType.SPECIAL;
        requiredOrbs = req;
        this.gain = gain;
    }
    public TomoeAction(int gain) {
        this(gain, AbstractDungeon.player.maxOrbs);
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.orbs.isEmpty() && p.filledOrbCount() >= requiredOrbs) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 1)));
        }
        this.isDone = true;
    }
}
