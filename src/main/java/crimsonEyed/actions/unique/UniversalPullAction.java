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
import java.util.Iterator;

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
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();// 51
                    Iterator var5 = group.group.iterator();// 52

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        cardsToMove.add(c);// 53
                    }

                    var5 = cardsToMove.iterator();// 55

                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        group.removeCard(c);
                        player.limbo.addToBottom(c);
                        addToBot(new ApplyPowerAction(player, player, new UniversalPullPower(player, 1, c)));
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
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();

                    group.removeCard(c);
                    //player.limbo.addToBottom(c);
                    addToBot(new ApplyPowerAction(player, player, new UniversalPullPower(player, 1, c)));
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
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
