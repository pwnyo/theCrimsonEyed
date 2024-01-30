package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardToTopAction extends AbstractGameAction {
    private AbstractCard card;

    public DiscardToTopAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.discardPile.isEmpty() && p.discardPile.contains(card)) {
                p.discardPile.removeCard(card);
                p.hand.moveToDeck(card, false);
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
