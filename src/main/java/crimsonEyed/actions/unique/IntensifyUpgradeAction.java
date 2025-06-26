package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.cards.basic.Hatred;
import crimsonEyed.cards.temp.EnduringFlame;

public class IntensifyUpgradeAction extends AbstractGameAction {
    AbstractPlayer p;
    boolean hasEnough;
    boolean hasHope;
    public IntensifyUpgradeAction(boolean hasHope) {
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        this.setValues(p, p, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.hasHope = hasHope;
    }

    public void update() {
        if (p.hand.size() > 0) {
            for (AbstractCard c : p.hand.group) {
                if (hasHope ? (c instanceof EnduringFlame) : (c instanceof Hatred)) {
                    hasEnough = true;
                    addToTop(new UpgradeSpecificCardAction(c));
                    c.upgrade();
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
