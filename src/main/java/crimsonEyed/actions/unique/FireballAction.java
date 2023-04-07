package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class FireballAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int multiplier;

    public FireballAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;

        p = AbstractDungeon.player;
        multiplier = 0;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                if (this.p.hand.getBottomCard().costForTurn == -1) {
                    multiplier += EnergyPanel.getCurrentEnergy();

                    this.addToTop(new DamageAllEnemiesAction(p, multiplier * amount, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                } else if (this.p.hand.getBottomCard().costForTurn > 0) {
                    multiplier += p.hand.getBottomCard().costForTurn;

                    this.addToTop(new DamageAllEnemiesAction(p, multiplier * amount, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                }
                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                this.tickDuration();
            } else {
               AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                p.hand.moveToExhaustPile(c);
                if (c.costForTurn == -1) {
                    multiplier += EnergyPanel.getCurrentEnergy();
                } else if (c.costForTurn > 0) {
                    multiplier += c.costForTurn;
                }
                this.addToTop(new DamageAllEnemiesAction(p, multiplier * amount, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(c)) {// 48 54
                    c = (AbstractCard)var1.next();
                    if (c.costForTurn == -1) {// 49
                        multiplier += EnergyPanel.getCurrentEnergy();
                    } else if (c.costForTurn > 0) {// 51
                        multiplier += c.costForTurn;
                    }
                    this.addToTop(new DamageAllEnemiesAction(p, multiplier * amount, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 56
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 57
            }

            this.tickDuration();// 60
        }
    }// 46
}
