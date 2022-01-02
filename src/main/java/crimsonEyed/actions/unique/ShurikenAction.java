package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShurikenAction extends AbstractGameAction {
    AbstractPlayer p;
    public ShurikenAction() {
        duration = Settings.ACTION_DUR_FAST;
        p = AbstractDungeon.player;
    }

    public void update() {
        int toMake = 10 - p.hand.size();
        addToBot(new MakeTempCardInHandAction(new Shiv(), toMake));

        this.tickDuration();
        this.isDone = true;
    }
}
