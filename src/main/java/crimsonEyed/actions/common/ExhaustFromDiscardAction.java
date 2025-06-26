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
import java.util.Iterator;

public class ExhaustFromDiscardAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean optional;

    public ExhaustFromDiscardAction(int amount, boolean optional) {
        this.p = AbstractDungeon.player;// 22
        this.optional = optional;// 23
        this.amount = amount;// 25
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;// 26
        this.actionType = ActionType.EXHAUST;// 27
    }// 28

    public void update() {
        if (this.duration == this.startDuration) {// 46
            if (!p.discardPile.isEmpty() && amount > 0) {// 47
                if (p.discardPile.size() <= amount && !this.optional) {// 50
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();// 51
                    Iterator var5 = p.discardPile.group.iterator();// 52

                    AbstractCard c;
                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        cardsToMove.add(c);// 53
                    }

                    var5 = cardsToMove.iterator();// 55

                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        p.discardPile.moveToExhaustPile(c);

                        c.lighten(false);// 63
                        c.applyPowers();// 64
                    }

                    this.isDone = true;// 67
                } else {
                    ///TODO: These UI strings don't work correctly
                    if (amount == 1) {// 70
                        if (this.optional) {// 71
                            AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, true, TEXT[0]);// 72
                        } else {
                            AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[0], false);// 78
                        }
                    } else if (this.optional) {// 85
                        AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, true, TEXT[1] + amount + TEXT[2]);// 86
                    } else {
                        AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[1] + amount + TEXT[2], false);// 92
                    }

                    this.tickDuration();// 99
                }
            } else {
                this.isDone = true;// 48
            }
        } else {
            Iterator var1;
            AbstractCard c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 104
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();// 105

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    p.discardPile.moveToExhaustPile(c);

                    c.lighten(false);// 113
                    c.unhover();// 114
                    c.applyPowers();// 115
                }

                for(var1 = p.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {// 117 120
                    c = (AbstractCard)var1.next();
                    c.unhover();// 118
                    c.target_x = (float)CardGroup.DISCARD_PILE_X;// 119
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");// 13
        TEXT = uiStrings.TEXT;// 14
    }
}
