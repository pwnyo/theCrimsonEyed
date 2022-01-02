package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ExhaustToDiscardAction extends AbstractGameAction {
    //private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public ExhaustToDiscardAction(AbstractCreature source, int count) {
        this.p = AbstractDungeon.player;// 18
        this.setValues((AbstractCreature)null, source, count);// 19
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FASTER;// 21
    }// 22

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {// 26
            this.isDone = true;// 27
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {// 30
                if (this.p.exhaustPile.isEmpty()) {// 31
                    this.isDone = true;// 32
                    return;// 33
                }

                if (this.p.exhaustPile.size() == 1) {// 34
                    AbstractCard tmp = this.p.exhaustPile.getTopCard();// 35
                    this.p.exhaustPile.removeCard(tmp);// 36
                    this.p.exhaustPile.moveToDeck(tmp, false);// 37
                }

                if (this.p.exhaustPile.group.size() > this.amount) {// 40
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, amount, TEXT[0], false, false, false, false);// 41
                    this.tickDuration();// 42
                    return;// 43
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 49
                Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();// 50

                while(var3.hasNext()) {
                    AbstractCard c = (AbstractCard)var3.next();
                    this.p.exhaustPile.removeCard(c);// 51
                    this.p.hand.moveToDeck(c, false);// 52
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 54
                AbstractDungeon.player.hand.refreshHandLayout();// 55
            }

            this.tickDuration();// 58
        }
    }// 28 59

    static {
        //uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustToDiscardAction");// 13
        TEXT = new String[] {"Choose a Card to Move to Your Discard Pile", "Choose Cards to Move to Your Discard Pile"};// 14
    }
}
