package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.UUID;

public class ExhaustAllButSomeAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private UUID uuid;
    private ArrayList<AbstractCard> keep = new ArrayList();

    public ExhaustAllButSomeAction(int amount) {
        this.actionType = ActionType.EXHAUST;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                this.uuid = this.p.hand.getTopCard().uuid;
                returnCards();
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                keep = AbstractDungeon.handCardSelectScreen.selectedCards.group;
                ArrayList<AbstractCard> exhaust = new ArrayList();

                for (int i = 0; i < p.hand.size(); i++) {
                    c = p.hand.group.get(i);
                    if (!keep.contains(c)) {
                        exhaust.add(c);
                    }
                }
                for (int i = 0; i < exhaust.size(); i++) {
                    p.hand.moveToExhaustPile(exhaust.get(i));
                }
                returnCards();
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

        }
        this.tickDuration();
    }
    private void returnCards() {
        for (AbstractCard c : keep) {
            p.hand.addToTop(c);
        }

        p.hand.refreshHandLayout();
    }

    static {
        TEXT = new String[]{ " a Card to Keep."};
    }
}

