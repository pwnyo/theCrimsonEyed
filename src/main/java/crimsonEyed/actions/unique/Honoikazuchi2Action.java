package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;

public class Honoikazuchi2Action extends AbstractGameAction {
    int damage;
    public Honoikazuchi2Action(AbstractMonster target, int damage) {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = target;
        this.damage = damage;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
                if (o instanceof Dark) {
                    addToBot(new DamageAction(target, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
                }
            }
        }
        this.isDone = true;
    }
}
