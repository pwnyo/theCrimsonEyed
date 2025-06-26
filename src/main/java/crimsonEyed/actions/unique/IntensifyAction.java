package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.actions.common.DrawToHandAction;
import crimsonEyed.cards.basic.Hatred;
import crimsonEyed.cards.temp.EnduringFlame;
import crimsonEyed.patches.interfaces.IOnIntensifyListener;
import crimsonEyed.relics.boss.ScratchedHeadband;

public class IntensifyAction extends AbstractGameAction {
    AbstractPlayer p;
    public IntensifyAction() {
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.setValues(p, p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        for (AbstractPower pow : p.powers) {
            if (pow instanceof IOnIntensifyListener) {
                ((IOnIntensifyListener) pow).onIntensify();
            }
        }

        boolean hasEnough = false;
        boolean hasHope = p.hasRelic(ScratchedHeadband.ID);

        if (p.hand.size() > 0) {
            for (AbstractCard c : p.hand.group) {
                if (hasHope ? (c instanceof EnduringFlame) : (c instanceof Hatred)) {
                    hasEnough = true;
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            }
        }

        if (p.drawPile.size() > 0) {
            for (AbstractCard c : p.drawPile.group) {
                if (hasHope ? (c instanceof EnduringFlame) : (c instanceof Hatred)) {
                    hasEnough = true;
                    addToTop(new DrawToHandAction(c));
                }
            }
        }
        if (p.discardPile.size() > 0) {
            for (AbstractCard c : p.discardPile.group) {
                if (hasHope ? (c instanceof EnduringFlame) : (c instanceof Hatred)) {
                    hasEnough = true;
                    addToTop(new DiscardToHandAction(c));
                }
                if (c instanceof IOnIntensifyListener) {
                    ((IOnIntensifyListener) c).onIntensify();
                }
            }
        }

        if (!hasEnough) {
            addToTop(new MakeTempCardInHandAction(hasHope ? new EnduringFlame() : new Hatred()));
        }

        this.tickDuration();
        this.isDone = true;
    }
}
