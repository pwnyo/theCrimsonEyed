package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CopycatAction extends AbstractGameAction {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    private AbstractPlayer player;
    private int copies;

    public CopycatAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.copies = 1;

        player = AbstractDungeon.player;
    }
    public CopycatAction(int amount, int copies) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.copies = copies;

        player = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 46
            if (!this.player.discardPile.isEmpty() && this.amount > 0) {
                if (this.player.discardPile.size() <= this.amount) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>();

                    for (AbstractCard c : player.discardPile.group) {
                        cardsToMove.add(c);
                    }

                    for (AbstractCard card : cardsToMove) {
                        AbstractCard temp = card.makeCopy();
                        temp.upgrade();
                        addToBot(new MakeTempCardInHandAction(temp));
                    }

                    this.isDone = true;// 67
                } else {
                    if (this.amount == 1) {// 70
                        AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.amount, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.amount, TEXT[1] + this.amount + TEXT[2], false);// 92
                    }

                    this.tickDuration();// 99
                }
            } else {
                this.isDone = true;// 48
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractCard temp = card.makeCopy();
                    temp.upgrade();
                    if (player.hand.size() < 10)
                        addToBot(new MakeTempCardInHandAction(temp));
                    else
                        addToBot(new MakeTempCardInDiscardAction(temp, copies));
                }
                for (AbstractCard c : player.discardPile.group) {
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
            if (this.isDone) {
                for (AbstractCard c : player.hand.group) {
                    c.applyPowers();
                }
            }

        }
    }// 28
}
