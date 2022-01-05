package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class SenbonAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private boolean upgraded;

    public SenbonAction(AbstractPlayer p, int[] multiDamage, DamageType damageType, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.multiDamage = multiDamage;// 28
        this.damageType = damageType;// 29
        this.p = p;// 30
        this.freeToPlayOnce = freeToPlayOnce;// 31
        this.duration = Settings.ACTION_DUR_XFAST;// 32
        this.actionType = ActionType.SPECIAL;// 33
        this.energyOnUse = energyOnUse;// 34
        this.upgraded = upgraded;
    }// 35

    public void update() {
        int effect = EnergyPanel.totalCount;// 39
        if (this.energyOnUse != -1) {// 40
            effect = this.energyOnUse;// 41
        }

        if (this.p.hasRelic("Chemical X")) {// 44
            effect += 2;// 45
            this.p.getRelic("Chemical X").flash();// 46
        }

        if (effect > 0) {

            for (int i = 0; i < effect; i++) {
                this.addToBot(new SFXAction("BLUNT_FAST"));// 56
                this.addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
                this.addToBot(new DamageAllEnemiesAction(this.p, multiDamage, this.damageType, AttackEffect.NONE, true));
            }

            for (int i = 0; i < (upgraded ? effect : effect - 1); i++) {
                this.addToBot(new ChannelAction(new Lightning()));
            }

            if (!this.freeToPlayOnce) {// 61
                this.p.energy.use(EnergyPanel.totalCount);// 62
            }
        }

        this.isDone = true;// 65
    }// 66
}
