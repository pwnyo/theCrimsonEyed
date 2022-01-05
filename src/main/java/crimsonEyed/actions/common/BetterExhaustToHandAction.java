package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class BetterExhaustToHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;
    private int newCost;
    private boolean setCost;

    public BetterExhaustToHandAction(int numberOfCards, boolean optional) {
        this.newCost = 0;
        this.actionType = ActionType.CARD_MANIPULATION;// 23
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;// 24
        this.player = AbstractDungeon.player;// 25
        this.numberOfCards = numberOfCards;// 26
        this.optional = optional;// 27
        this.setCost = false;// 28
    }// 29

    public BetterExhaustToHandAction(int numberOfCards) {
        this(numberOfCards, false);// 32
    }// 33

    public BetterExhaustToHandAction(int numberOfCards, int newCost) {
        this.newCost = 0;// 19
        this.actionType = ActionType.CARD_MANIPULATION;// 36
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;// 37
        this.player = AbstractDungeon.player;// 38
        this.numberOfCards = numberOfCards;// 39
        this.optional = false;// 40
        this.setCost = true;// 41
        this.newCost = newCost;// 42
    }// 43

    public void update() {
        if (this.duration == this.startDuration) {// 46
            if (!this.player.exhaustPile.isEmpty() && this.numberOfCards > 0) {// 47
                if (this.player.exhaustPile.size() <= this.numberOfCards && !this.optional) {// 50
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();// 51
                    Iterator var5 = this.player.exhaustPile.group.iterator();// 52

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        cardsToMove.add(c);// 53
                    }

                    var5 = cardsToMove.iterator();// 55

                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        if (this.player.hand.size() < 10) {// 56
                            this.player.hand.addToHand(c);// 57
                            if (this.setCost) {// 58
                                c.setCostForTurn(this.newCost);// 59
                            }

                            this.player.exhaustPile.removeCard(c);// 61
                        }

                        c.lighten(false);// 63
                        c.applyPowers();// 64
                    }

                    this.isDone = true;// 67
                } else {
                    if (this.numberOfCards == 1) {// 70
                        if (this.optional) {// 71
                            AbstractDungeon.gridSelectScreen.open(this.player.exhaustPile, this.numberOfCards, true, TEXT[0]);// 72
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.player.exhaustPile, this.numberOfCards, TEXT[0], false);// 78
                        }
                    } else if (this.optional) {// 85
                        AbstractDungeon.gridSelectScreen.open(this.player.exhaustPile, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);// 86
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.exhaustPile, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);// 92
                    }

                    this.tickDuration();// 99
                }
            } else {
                this.isDone = true;// 48
            }
        } else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (this.player.hand.size() < 10) {
                        this.player.hand.addToHand(c);
                        if (this.setCost) {
                            c.setCostForTurn(this.newCost);
                        }

                        this.player.exhaustPile.removeCard(c);
                    }

                    c.lighten(false);
                    c.unhover();
                    c.applyPowers();
                }

                for(var1 = this.player.exhaustPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {// 117 120
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
            if (this.isDone) {
                var1 = this.player.hand.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    c.applyPowers();
                }
            }

        }
    }// 49 68 100 132

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;// 15
    }
}
