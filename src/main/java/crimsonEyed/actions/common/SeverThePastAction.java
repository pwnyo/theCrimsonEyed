package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SeverThePastAction extends AbstractGameAction {
    private float startingDuration;

    public SeverThePastAction(int block) {
        this.actionType = ActionType.EXHAUST;// 16
        this.startingDuration = Settings.ACTION_DUR_FAST;// 17
        this.duration = this.startingDuration;// 18
        this.amount = block;
    }// 19

    public void update() {
        if (this.duration == this.startingDuration) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractCard c : p.discardPile.group) {
                //AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
                this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
            }

            this.isDone = true;
        }

    }// 36
}