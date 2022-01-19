package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crimsonEyed.SasukeMod;

public class UchiwaAction extends AbstractGameAction {
    private AbstractRelic relic;
    public UchiwaAction(AbstractRelic relic) {
        this.actionType = ActionType.DRAW;
        this.relic = relic;
    }

    public void update() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.isInnate)
                count++;
        }
        SasukeMod.logger.info("size " + AbstractDungeon.player.hand.size());
        SasukeMod.logger.info(count);
        if (count > 0) {
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, relic));
            addToBot(new DrawCardAction(count));
        }
        tickDuration();
        isDone = true;
    }
}
