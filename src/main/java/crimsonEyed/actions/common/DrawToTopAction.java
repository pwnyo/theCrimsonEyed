package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawToTopAction extends AbstractGameAction {
    private AbstractCard card;

    public DrawToTopAction(AbstractCard card) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.drawPile.isEmpty() && p.drawPile.contains(card)) {
                p.drawPile.removeCard(card);
                p.drawPile.moveToDeck(card, false);
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
