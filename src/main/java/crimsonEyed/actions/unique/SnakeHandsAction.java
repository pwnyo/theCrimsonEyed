package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SnakeHandsAction extends AbstractGameAction {
    private DamageInfo info;

    public SnakeHandsAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int hits = hasStatusOrCurse() ? 3 : 1;
        for (int i = 0; i < hits; i++) {
            addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.POISON));
        }
        this.tickDuration();
        this.isDone = true;
    }

    public static boolean hasStatusOrCurse() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (isStatusOrCurse(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStatusOrCurse(AbstractCard c) {
        return c.type == AbstractCard.CardType.STATUS || c.type == AbstractCard.CardType.CURSE;
    }
}
