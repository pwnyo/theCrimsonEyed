package crimsonEyed.actions.unique;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class ExhaustAllCopiesFromDiscardAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean optional;
    private int exhaustedCount = 0;

    public ExhaustAllCopiesFromDiscardAction(int amount, boolean optional) {
        this.actionType = ActionType.EXHAUST;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.optional = optional;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 46
            if (!this.p.discardPile.isEmpty() && this.amount > 0) {// 47
                if (this.p.discardPile.size() <= this.amount && !this.optional) {// 50

                    for (int i = 0; i < p.discardPile.size(); ++i) {
                        AbstractCard c = p.discardPile.getTopCard();
                        addToBot(new VFXAction(new ExhaustCardEffect(c)));
                        p.discardPile.moveToExhaustPile(c);
                    }

                    this.isDone = true;// 67
                } else {
                    if (this.amount == 1) {// 70
                        if (this.optional) {// 71
                            AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, true, TEXT[0]);// 72
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[0], false);// 78
                        }
                    } else if (this.optional) {// 85
                        AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, true, TEXT[1] + this.amount + TEXT[2]);// 86
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, TEXT[1] + this.amount + TEXT[2], false);// 92
                    }

                    this.tickDuration();// 99
                }
            } else {
                BaseMod.logger.info("d2e else");
                this.isDone = true;// 48
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                for (int i = 0; i < AbstractDungeon.gridSelectScreen.selectedCards.size(); ++i) {
                    AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(i);
                    exhaustWithAnimation(c);
                    for (AbstractCard extra : p.discardPile.group) {
                        if (extra.cardID == c.cardID) {
                            exhaustWithAnimation(extra);
                        }
                    }
                }
                if (exhaustedCount > 0) {
                    addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, exhaustedCount));
                    addToBot(new DrawCardAction(exhaustedCount));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.tickDuration();

        }
    }
    void exhaustWithAnimation(AbstractCard c) {
        addToBot(new VFXAction(new ExhaustCardEffect(c)));
        p.discardPile.moveToExhaustPile(c);

        exhaustedCount++;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT;// 11
    }
}
