package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustFromDiscardTopAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ExhaustFromDiscardTopAction(int count) {
        this.p = AbstractDungeon.player;// 18
        this.setValues(p, p, count);// 19
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FASTER;// 21
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            AbstractCard c1, c2;
            if (p.discardPile.size() < amount) {
                for (AbstractCard c : p.discardPile.group) {
                    p.discardPile.moveToExhaustPile(c);
                }
            }
            else {
                for (int i = 0; i < amount; i++) {
                    p.discardPile.moveToExhaustPile(p.discardPile.getTopCard());
                }
            }
        }

        this.tickDuration();
    }
}
