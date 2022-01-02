package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CopyFromTopAction extends AbstractGameAction {
    private boolean upgrade;

    public CopyFromTopAction(int amount, boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            /*
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {// 26
                this.isDone = true;// 27
                return;// 28
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;// 34
                return;// 35
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {// 38
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                card.upgrade();
                addToTop(new MakeTempCardInDiscardAction(card, amount));
            }

             */
            if (AbstractDungeon.player.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            else {
                AbstractCard card = AbstractDungeon.player.discardPile.getTopCard();
                if (upgrade) {
                    card.upgrade();
                }
                addToTop(new MakeTempCardInDiscardAction(card, amount));
            }
            this.isDone = true;// 61
        }
    }
}
