package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class AlmightyPushAction extends AbstractGameAction {
    public AlmightyPushAction(int dmg) {
        amount = dmg;// 14
    }
    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        addToBot(new VFXAction(new BlizzardEffect(amount, AbstractDungeon.getMonsters().shouldFlipVfx())));
        addToBot(new ShakeScreenAction(0.0F, ScreenShake.ShakeDur.XLONG, ScreenShake.ShakeIntensity.HIGH));
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SMASH));

        this.isDone = true;
    }
}
