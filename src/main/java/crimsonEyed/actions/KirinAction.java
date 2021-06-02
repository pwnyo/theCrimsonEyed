package crimsonEyed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import crimsonEyed.powers.LoseFocusPower;

public class KirinAction extends AbstractGameAction {
    public KirinAction(int focus) {
        this.amount = focus;
        this.duration = Settings.ACTION_DUR_XFAST;
    }// 16

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, amount)));
        this.addToTop(new ApplyPowerAction(p, p, new LoseFocusPower(p, amount)));
        if (this.duration == Settings.ACTION_DUR_XFAST && !AbstractDungeon.player.orbs.isEmpty()) {
            this.addToTop(new EvokeAllOrbsAction());
        }
        this.addToTop(new EndTurnAction());
        this.tickDuration();// 31
    }
}
