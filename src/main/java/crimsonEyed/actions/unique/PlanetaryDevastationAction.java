package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class PlanetaryDevastationAction extends AbstractGameAction {
    public PlanetaryDevastationAction() {
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int count = 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower pow : mo.powers) {
                    if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled")) {
                        count++;
                        addToBot(new VFXAction(new WeightyImpactEffect(mo.hb.cX, mo.hb.cY), 0));
                        break;
                    }
                }
            }
            for (int i = 0; i < count; i++) {
                addToBot(new ChannelAction(new Plasma()));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
