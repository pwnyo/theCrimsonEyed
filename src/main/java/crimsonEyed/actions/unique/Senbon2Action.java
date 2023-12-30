package crimsonEyed.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class Senbon2Action extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private boolean upgraded;

    public Senbon2Action(AbstractPlayer p, int[] multiDamage, DamageType damageType, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;// 45
            this.p.getRelic("Chemical X").flash();// 46
        }

        if (effect > 0) {

            for (int i = 0; i < effect; i++) {
                this.addToBot(new SFXAction("BLUNT_FAST"));// 56
                this.addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
                this.addToBot(new DamageAllEnemiesAction(this.p, multiDamage, this.damageType, AttackEffect.NONE, true));
            }

            for (int i = 0; i < effect; i++) {
                this.addToBot(new TriggerPassiveAction());
            }

            if (!this.freeToPlayOnce) {// 61
                this.p.energy.use(EnergyPanel.totalCount);// 62
            }
        }

        this.isDone = true;// 65
    }// 66
}
