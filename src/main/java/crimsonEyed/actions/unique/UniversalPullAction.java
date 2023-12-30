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

import java.util.ArrayList;

public class UniversalPullAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    CardGroup group;

    public UniversalPullAction(int numberOfCards, CardGroup.CardGroupType groupType) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        if (groupType == CardGroup.CardGroupType.DISCARD_PILE) {
            group = player.discardPile;
        }
        else if (groupType == CardGroup.CardGroupType.EXHAUST_PILE) {
            group = player.exhaustPile;
        }
        else {
            group = player.drawPile;
        }
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!group.isEmpty() && this.numberOfCards > 0) {
                if (group.size() <= this.numberOfCards) {// 50
                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>();// 51
                    for (AbstractCard c : group.group) {
                        cardsToMove.add(c);
                    }
                    for (AbstractCard c : cardsToMove) {
                        cardsToMove.add(c);
                        group.removeCard(c);
                        addToTop(new ApplyPowerAction(player, player, new UniversalPullPower(player, 1, c)));
                    }

                    this.isDone = true;// 67
                } else {
                    if (this.numberOfCards == 1) {
                        AbstractDungeon.gridSelectScreen.open(group, this.numberOfCards, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(group, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    group.removeCard(c);
                    addToTop(new ApplyPowerAction(player, player, new UniversalPullPower(player, 1, c)));
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
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
