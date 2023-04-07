package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AttackFromDiscardToHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public AttackFromDiscardToHandAction(int amount) {
        this.p = AbstractDungeon.player;// 20
        this.setValues(this.p, AbstractDungeon.player, amount);// 21
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.duration = Settings.ACTION_DUR_MED;// 23
    }// 24

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {// 28
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.discardPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    tmp.addToRandomSpot(c);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    this.p.createHandIsFullDialog();
                } else {
                    card.unhover();// 46
                    card.lighten(true);// 47
                    card.setAngle(0.0F);// 48
                    card.drawScale = 0.12F;// 49
                    card.targetDrawScale = 0.75F;// 50
                    card.current_x = CardGroup.DRAW_PILE_X;// 51
                    card.current_y = CardGroup.DRAW_PILE_Y;// 52
                    this.p.discardPile.removeCard(card);// 53
                    AbstractDungeon.player.hand.addToTop(card);// 54
                    AbstractDungeon.player.hand.refreshHandLayout();// 55
                    AbstractDungeon.player.hand.applyPowers();// 56
                }

                this.isDone = true;// 58
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);// 62
                this.tickDuration();// 63
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.createHandIsFullDialog();
                    } else {
                        this.p.discardPile.removeCard(c);
                        this.p.hand.addToTop(c);// 78
                    }

                    this.p.hand.refreshHandLayout();// 80
                    this.p.hand.applyPowers();// 81
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 83
                this.p.hand.refreshHandLayout();// 84
            }
            this.tickDuration();// 87
        }
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AttackFromDeckToHandAction");
        TEXT = uiStrings.TEXT;
    }
}
