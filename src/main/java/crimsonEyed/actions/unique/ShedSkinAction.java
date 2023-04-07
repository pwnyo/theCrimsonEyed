package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.UnceasingTop;

import java.util.Iterator;

import static basemod.BaseMod.logger;

public class ShedSkinAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;

    public ShedSkinAction(AbstractCard c, int amount) {
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
        card = c;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            if (this.p.hand.isEmpty()) {// 27
                this.isDone = true;// 28
            } else if (this.p.hand.size() == 1) {// 30
                if (this.p.hand.getBottomCard().type == AbstractCard.CardType.CURSE || p.hand.getBottomCard().type == AbstractCard.CardType.STATUS) {// 31
                    regen();
                }

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());// 36
                this.tickDuration();// 37
            } else {
                AbstractDungeon.handCardSelectScreen.open("Exhaust", 1, false);// 40
                this.tickDuration();// 41
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {// 49
                        addToTop(new ApplyPowerAction(p, p, new RegenPower(p, amount)));
                        addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
                    }
                    p.hand.moveToExhaustPile(c);
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();// 60
        }
    }
    void regen() {
    }
}
