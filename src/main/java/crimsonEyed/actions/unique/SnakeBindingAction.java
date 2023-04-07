package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import crimsonEyed.SasukeMod;

public class SnakeBindingAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractCard parent;

    public SnakeBindingAction(int amount, AbstractCard parent) {
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
        p = AbstractDungeon.player;
        this.parent = parent;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (Settings.FAST_MODE) {// 44
                this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));// 45
            } else {
                this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));// 51
            }
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                applyBinding(mo);
            }
            if (!SnakeHandsAction.hasStatusOrCurse()) {
                SasukeMod.logger.info("binding should exhaust");
                addToBot(new ExhaustSpecificCardAction(parent, p.discardPile));
            }
        }

        this.tickDuration();
    }

    void applyBinding(AbstractMonster mo) {
        addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amount), -amount, true, AbstractGameAction.AttackEffect.NONE));
        if (!mo.hasPower(ArtifactPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
