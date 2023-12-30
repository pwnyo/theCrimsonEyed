package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustDamageAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int multiplier;

    public ExhaustDamageAction(int damage, int multiplier) {
        this.amount = damage;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;

        p = AbstractDungeon.player;
        this.multiplier = multiplier;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            this.addToTop(new DamageAllEnemiesAction(p, amount + p.exhaustPile.size() * multiplier, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
        }
        this.tickDuration();
        this.isDone = true;
    }
}
