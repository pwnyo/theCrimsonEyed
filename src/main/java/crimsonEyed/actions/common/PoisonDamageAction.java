package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class PoisonDamageAction extends AbstractGameAction {
    private DamageInfo info;

    public PoisonDamageAction(AbstractCreature target, DamageInfo info) {
        this.info = info;// 18
        this.setValues(target, info);// 19
        this.actionType = ActionType.DAMAGE;// 20
        this.startDuration = Settings.ACTION_DUR_FAST;// 21
        this.duration = this.startDuration;// 22
    }// 23

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_LIGHT, false));
                this.target.damage(this.info);// 37
                if (this.target.lastDamageTaken > 0) {// 38
                    addToTop(new ApplyPowerAction(target, source, new PoisonPower(target, source, this.target.lastDamageTaken)));
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 45
                    AbstractDungeon.actionManager.clearPostCombatActions();// 46
                } else {
                    this.addToTop(new WaitAction(0.1F));// 48
                }
            }

        }
    }// 29 51
}