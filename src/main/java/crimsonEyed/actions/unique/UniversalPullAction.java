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
            if (!group.isEmpty() && this.numberOfCards > 0) {// 47
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
                        if (this.player.hand.size() < 10) {// 56
                            this.player.hand.addToHand(c);

                            group.removeCard(c);// 61
                        }

                        c.lighten(false);// 63
                        c.applyPowers();// 64
                    }

                    this.isDone = true;// 67
                } else {
                    if (this.numberOfCards == 1) {
                        AbstractDungeon.gridSelectScreen.open(group, this.numberOfCards, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(group, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);// 92
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 104
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();// 105

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (this.player.hand.size() < 10) {
                        group.removeCard(c);
                        player.limbo.addToBottom(c);
                        addToBot(new ApplyPowerAction(player, player, new UniversalPullPower(player, 1, c)));
                    }

                    c.lighten(false);// 113
                    c.unhover();// 114
                    c.applyPowers();// 115
                }

                for(var1 = group.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {// 117 120
                    c = (AbstractCard)var1.next();
                    c.unhover();// 118
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;// 119
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 122
                AbstractDungeon.player.hand.refreshHandLayout();// 123
            }

            this.tickDuration();// 125
            if (this.isDone) {// 127
                var1 = this.player.hand.group.iterator();// 128

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    c.applyPowers();// 129
                }
            }

        }
    }// 49 68 100 132

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;// 15
    }
}
