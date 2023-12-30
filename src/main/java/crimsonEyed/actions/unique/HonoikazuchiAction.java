package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class HonoikazuchiAction extends AbstractGameAction {
    boolean upgraded;
    public HonoikazuchiAction() {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.orbs.isEmpty() && !(p.orbs.get(0) instanceof EmptyOrbSlot)) {
                AbstractOrb orb = p.orbs.get(0);
                int damage = orb.evokeAmount;
                addToBot(new EvokeOrbAction(1));
                addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
