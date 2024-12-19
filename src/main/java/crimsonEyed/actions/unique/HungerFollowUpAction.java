package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Scrape;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class HungerFollowUpAction extends AbstractGameAction {
    public HungerFollowUpAction() {
        this.duration = 0.001F;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            int count = 0;
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                    count++;
                    AbstractDungeon.player.hand.moveToExhaustPile(c);
                    c.triggerOnExhaust();
                }
            }
            if (count > 0) {
                addToTop(new DrawCardAction(count));
            }
        }

    }// 31
}
