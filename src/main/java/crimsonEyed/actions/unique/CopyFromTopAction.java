package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CopyFromTopAction extends AbstractGameAction {
    private boolean upgrade;

    public CopyFromTopAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            else {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractCard copy = card.makeStatEquivalentCopy();
                addToTop(new ExhaustSpecificCardAction(card, p.drawPile));
                if (upgrade)
                    copy.upgrade();
                addToTop(new MakeTempCardInHandAction(copy));
            }
            this.isDone = true;// 61
        }
    }
}
