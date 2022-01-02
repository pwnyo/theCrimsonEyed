package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

import static basemod.BaseMod.logger;

public class AnywhereToHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> grabs = new ArrayList();
    private CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private int drawCount, discardCount, exhaustCount;

    public AnywhereToHandAction(int amount) {
        this.p = AbstractDungeon.player;// 18
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_MED;// 21
    }// 22

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {// 29
            CardGroup draw = new CardGroup(p.drawPile, CardGroup.CardGroupType.UNSPECIFIED);
            CardGroup discard = new CardGroup(p.discardPile, CardGroup.CardGroupType.UNSPECIFIED);
            CardGroup exhaust = new CardGroup(p.exhaustPile, CardGroup.CardGroupType.UNSPECIFIED);
            draw.sortAlphabetically(true);
            draw.sortByRarityPlusStatusCardType(false);
            discard.sortAlphabetically(true);
            discard.sortByRarityPlusStatusCardType(false);
            exhaust.sortAlphabetically(true);
            exhaust.sortByRarityPlusStatusCardType(false);
            drawCount = draw.size();
            discardCount = discard.size();
            exhaustCount = exhaust.size();

            for (AbstractCard c : draw.group) {
                tmp.addToTop(c);
            }
            for (AbstractCard c : discard.group) {
                tmp.addToTop(c);
            }
            for (AbstractCard c : exhaust.group) {
                tmp.addToTop(c);
            }

            if (tmp.size() == 0) {// 37
                this.isDone = true;// 38
            } else if (tmp.size() == 1) {// 40
                card = tmp.getTopCard();// 41
                if (this.p.hand.size() == 10) {// 43
                    findAndMoveToDiscard(card);
                    this.p.createHandIsFullDialog();// 45
                } else {
                    findAndMoveToHand(card);
                    AbstractDungeon.player.hand.refreshHandLayout();// 56
                    AbstractDungeon.player.hand.applyPowers();// 57
                }

                this.isDone = true;// 59
            } else {
                if (this.amount == 1) {// 81
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);// 82
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);// 84
                }
                this.tickDuration();// 64
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {// 70
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.unhover();
                    if (this.p.hand.size() == 10) {// 74
                        findAndMoveToDiscard(c);
                    } else {
                        findAndMoveToHand(c);
                    }

                    this.p.hand.refreshHandLayout();// 81
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 84
                this.p.hand.refreshHandLayout();// 85
            }

            this.tickDuration();// 88
        }
    }
    void findAndMoveToDiscard(AbstractCard card) {
        int index = tmp.group.indexOf(card);
        if (index < drawCount) {
            p.drawPile.moveToDiscardPile(card);
        }
        else if (index >= drawCount && index < drawCount + discardCount) {

        }
        else {
            p.exhaustPile.moveToDiscardPile(card);
        }
        p.createHandIsFullDialog();
    }
    void findAndMoveToHand(AbstractCard card) {
        int index = tmp.group.indexOf(card);
        logger.info("card index is " + index);
        logger.info("card is " + card.name);
        if (index < drawCount) {
            p.drawPile.moveToHand(card);
        }
        else if (index >= drawCount && index < drawCount + discardCount) {
            p.discardPile.moveToHand(card);
        }
        else {
            p.exhaustPile.moveToHand(card);
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");// 13
        TEXT = uiStrings.TEXT;// 14
    }
}
