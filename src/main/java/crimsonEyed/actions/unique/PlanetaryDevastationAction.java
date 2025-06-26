package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class PlanetaryDevastationAction extends AbstractGameAction {
    public PlanetaryDevastationAction(AbstractMonster target, int amount) {
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = target;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            applyPlanet((AbstractMonster)target);
        }

        this.tickDuration();
        this.isDone = true;
    }
    void applyPlanet(AbstractMonster m) {
        for (int i = 0; i < amount; i++) {
            addToTop(new ChannelAction(new Plasma()));
        }
        addToTop(new IncreaseMaxOrbAction(amount));
        addToTop(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY), 0.1f));
    }
}
