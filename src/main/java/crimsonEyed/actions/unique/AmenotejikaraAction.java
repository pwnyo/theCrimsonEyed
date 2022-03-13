package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.cards.temp.spacetime.Amenotejikara;

import java.util.ArrayList;
import java.util.Iterator;

public class AmenotejikaraAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public AmenotejikaraAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public AmenotejikaraAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();// 38
                    var6 = this.player.drawPile.group.iterator();// 39

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (!c.cardID.equals(Amenotejikara.ID))
                            cardsToMove.add(c);
                    }

                    var6 = cardsToMove.iterator();// 42

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (this.player.hand.size() == 10) {// 43
                            this.player.drawPile.moveToDiscardPile(c);// 44
                            this.player.createHandIsFullDialog();// 45
                        } else {
                            this.player.drawPile.moveToHand(c, this.player.drawPile);// 47
                        }
                    }

                    this.isDone = true;// 50
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);// 53
                    var6 = this.player.drawPile.group.iterator();// 54

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (!c.cardID.equals(Amenotejikara.ID))
                            temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);// 57
                    temp.sortByRarityPlusStatusCardType(false);// 58
                    if (this.numberOfCards == 1) {// 59
                        if (this.optional) {// 60
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);// 61
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);// 63
                        }
                    } else if (this.optional) {// 66
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);// 67
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);// 73
                    }

                    this.tickDuration();// 80
                }
            } else {
                this.isDone = true;// 35
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 84
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();// 85

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (this.player.hand.size() == 10) {// 86
                        this.player.drawPile.moveToDiscardPile(c);// 87
                        this.player.createHandIsFullDialog();// 88
                    } else {
                        this.player.drawPile.moveToHand(c, this.player.drawPile);// 90
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 93
                AbstractDungeon.player.hand.refreshHandLayout();// 94
            }

            this.tickDuration();// 96
        }
    }// 36 51 81 97

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;// 15
    }
}
