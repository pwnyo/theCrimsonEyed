package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.powers.UniversalPullPower;

public class UniversalPullAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int numberOfCards;
    int drawCount = 0, discardCount = 0, exhaustCount = 0;
    CardGroup combined;

    public UniversalPullAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (numberOfCards == 0 ||
                    (p.drawPile.isEmpty() && p.discardPile.isEmpty() && p.exhaustPile.isEmpty())) {
                this.isDone = true;
            } else {
                CardGroup sortedDraw = makeSortedGroup(p.drawPile);
                CardGroup sortedDiscard = makeSortedGroup(p.discardPile);
                CardGroup sortedExhaust = makeSortedGroup(p.exhaustPile);
                drawCount = sortedDraw.group.size();
                discardCount = sortedDiscard.group.size();
                exhaustCount = sortedExhaust.group.size();

                combined = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (drawCount > 0) {
                    for (AbstractCard c : sortedDraw.group) {
                        combined.addToTop(c);
                    }
                }
                if (discardCount > 0) {
                    for (AbstractCard c : sortedDiscard.group) {
                        combined.addToTop(c);
                    }
                }
                if (exhaustCount > 0) {
                    for (AbstractCard c : sortedExhaust.group) {
                        combined.addToTop(c);
                    }
                }

                if (combined.group.size() <= numberOfCards) {
                    for (int i = 0; i < combined.group.size(); i++) {
                        pullCardFromIndex(combined.group.get(i), i);
                    }
                    isDone = true;
                    return;
                }
                else if (this.numberOfCards == 1) {
                    AbstractDungeon.gridSelectScreen.open(combined, this.numberOfCards, TEXT[0], false);
                } else {
                    AbstractDungeon.gridSelectScreen.open(combined, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                }
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    int index = combined.group.indexOf(c);
                    pullCardFromIndex(c, index);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
    public CardGroup makeSortedGroup(CardGroup parentGroup) {
        CardGroup group  = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (!parentGroup.isEmpty()) {
            for (AbstractCard c : parentGroup.group) {
                group.addToTop(c);
            }
            group.sortAlphabetically(true);
            group.sortByRarityPlusStatusCardType(false);
        }

        return group;
    }

    void pullCardFromIndex(AbstractCard c, int index) {
        //considering combined is a grouping of draw-discard-exhaust piles,
        //we can determine the appropriate pile by adding pile counts until
        //we get a number higher than the index
        if (drawCount > index) {
            pullCard(c, p.drawPile);
        }
        else if (drawCount + discardCount > index) {
            pullCard(c, p.discardPile);
        }
        else if (drawCount + discardCount + exhaustCount > index) {
            pullCard(c, p.exhaustPile);
        }
        else {
            //shouldn't happen...
        }
    }
    void pullCard(AbstractCard c, CardGroup group)
    {
        group.removeCard(c);
        addToTop(new ApplyPowerAction(p, p, new UniversalPullPower(p, 1, c)));

        c.lighten(false);
        c.unhover();

        p.hand.refreshHandLayout();
        p.hand.applyPowers();
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
