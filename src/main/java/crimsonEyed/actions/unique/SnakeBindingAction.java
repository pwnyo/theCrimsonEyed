package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class SnakeBindingAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractCard card;

    public SnakeBindingAction(AbstractCard c, int amount) {
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
        p = AbstractDungeon.player;
        this.card = c;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                applyBinding(mo);
            }
            if (Settings.FAST_MODE) {// 44
                this.addToTop(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));// 45
            } else {
                this.addToTop(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));// 51
            }
            if (!SnakeHandsAction.hasStatusOrCurse()) {
                card.exhaust = true;
                addToBot(new ExhaustSpecificCardAction(card, p.discardPile));
            }
        }

        this.tickDuration();
    }

    void applyBinding(AbstractMonster mo) {
        addToTop(new ApplyPowerAction(mo, p, new StrengthPower(mo, -amount), -amount, true, AbstractGameAction.AttackEffect.NONE));
        if (!mo.hasPower(ArtifactPower.POWER_ID)) {
            addToTop(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
