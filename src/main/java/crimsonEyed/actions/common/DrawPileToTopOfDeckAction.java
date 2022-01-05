package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class DrawPileToTopOfDeckAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public DrawPileToTopOfDeckAction(AbstractCreature source) {
        this.p = AbstractDungeon.player;
        this.setValues((AbstractCreature)null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (this.p.drawPile.size() == 1) {
                    AbstractCard tmp = this.p.drawPile.getTopCard();
                    this.p.drawPile.removeCard(tmp);
                    this.p.drawPile.moveToDeck(tmp, false);
                }

                if (this.p.drawPile.group.size() > this.amount) {
                    AbstractDungeon.gridSelectScreen.open(this.p.drawPile, 1, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var3.hasNext()) {
                    AbstractCard c = (AbstractCard)var3.next();
                    this.p.drawPile.removeCard(c);
                    this.p.hand.moveToDeck(c, false);// 52
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 54
                AbstractDungeon.player.hand.refreshHandLayout();// 55
            }

            this.tickDuration();// 58
        }
    }// 28 59

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");// 13
        TEXT = uiStrings.TEXT;// 14
    }
}
