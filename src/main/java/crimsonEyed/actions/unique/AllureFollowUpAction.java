package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class AllureFollowUpAction extends AbstractGameAction {
    public AllureFollowUpAction() {
        this.duration = 0.001F;// 13
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            boolean drewBadStuff = false;
            Iterator var1 = DrawCardAction.drawnCards.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                    drewBadStuff = true;
                }
            }
            if (drewBadStuff) {
                addToBot(new DrawCardAction(2));
            }
        }

    }// 31
}
