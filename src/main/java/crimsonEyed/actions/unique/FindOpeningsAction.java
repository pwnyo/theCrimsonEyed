package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FindOpeningsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public FindOpeningsAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;// 19
        this.p = AbstractDungeon.player;// 20
        this.duration = Settings.ACTION_DUR_FAST;// 21
    }// 22

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            if (this.p.hand.isEmpty()) {// 27
                this.isDone = true;// 28
            } else if (this.p.hand.size() == 1) {// 30
                if (this.p.hand.getBottomCard().costForTurn == -1) {// 31
                    this.addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));// 32
                } else if (this.p.hand.getBottomCard().costForTurn > 0) {// 33
                    this.addToTop(new GainEnergyAction(this.p.hand.getBottomCard().costForTurn));// 34
                }

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());// 36
                this.tickDuration();// 37
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);// 40
                this.tickDuration();// 41
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    if (c.costForTurn == -1) {// 49
                        this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, EnergyPanel.getCurrentEnergy())));
                    } else if (c.costForTurn > 0) {// 51
                        this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, c.costForTurn)));
                    }
                    p.hand.moveToExhaustPile(c);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 56
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 57
            }

            this.tickDuration();// 60
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");// 14
        TEXT = uiStrings.TEXT;// 15
    }
}
