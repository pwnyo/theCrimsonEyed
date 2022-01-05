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
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
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

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    findAndMoveToDiscard(card);
                    this.p.createHandIsFullDialog();
                } else {
                    findAndMoveToHand(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                this.isDone = true;
            } else {
                if (this.amount == 1) {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
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
