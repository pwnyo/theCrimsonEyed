package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SeverThePastAction extends AbstractGameAction {
    private float startingDuration;

    public SeverThePastAction() {
        this.actionType = ActionType.EXHAUST;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractCard c : p.discardPile.group) {
                //AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
            }

            this.isDone = true;
        }

    }
}
