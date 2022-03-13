package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;

import java.util.Iterator;

import static basemod.BaseMod.logger;

public class ShedSkinAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean upgraded;

    public ShedSkinAction(int amount, boolean upgraded) {
        this.amount = amount;
        this.upgraded = upgraded;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 26
            if (this.p.hand.isEmpty()) {// 27
                this.isDone = true;// 28
            } else if (this.p.hand.size() == 1) {// 30
                if (this.p.hand.getBottomCard().type == AbstractCard.CardType.CURSE || p.hand.getBottomCard().type == AbstractCard.CardType.STATUS) {// 31
                    regen();
                } else  {// 33
                    logger.info("exhausted non-curse");
                }

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());// 36
                this.tickDuration();// 37
            } else {
                AbstractDungeon.handCardSelectScreen.open("Exhaust", 1, false);// 40
                this.tickDuration();// 41
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 47
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(c)) {// 48 54
                    c = (AbstractCard)var1.next();
                    if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {// 49
                        regen();
                    } else {// 51
                        logger.info("exhausted non-curse");
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 56
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 57
            }

            this.tickDuration();// 60
        }
    }
    void regen() {
        logger.info("exhausted curse");
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, amount)));
        addToBot(new GainEnergyAction(upgraded ? 3 : 2));
    }
}
