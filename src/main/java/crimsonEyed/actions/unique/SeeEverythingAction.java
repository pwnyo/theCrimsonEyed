package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static crimsonEyed.SasukeMod.makeID;

public class SeeEverythingAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;

    public SeeEverythingAction(boolean freeToPlayOnce, int energyOnUse, int bonus) {
        this.actionType = ActionType.DEBUFF;
        this.duration = Settings.ACTION_DUR_FAST;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.amount = bonus;

        this.p = AbstractDungeon.player;
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int effect = EnergyPanel.totalCount;

            if (this.energyOnUse != -1) {
                effect = this.energyOnUse;
            }

            effect += amount;

            if (p.hasRelic("Chemical X")) {
                effect += 2;
                p.getRelic("Chemical X").flash();
            }

            if (effect > 0) {
                addToBot(new SFXAction(makeID("SHARINGAN")));
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    this.addToBot(new ApplyPowerAction(m, p, new LockOnPower(m, effect), effect, true, AbstractGameAction.AttackEffect.NONE));
                    this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, effect, false), effect, true, AbstractGameAction.AttackEffect.NONE));
                    this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, effect, false), effect, true, AbstractGameAction.AttackEffect.NONE));
                }
                if (!this.freeToPlayOnce) {
                    p.energy.use(EnergyPanel.totalCount);
                }
            }
        }
        this.isDone = true;
    }
}
