package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import crimsonEyed.SasukeMod;

public class BlazeBarrierAction extends AbstractGameAction {
    public BlazeBarrierAction(int amount) {
        this.actionType = ActionType.POWER;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int darkCount = 0;
            for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
                if (o instanceof Dark)
                    darkCount++;
            }
            SasukeMod.logger.info("You have this many Dark: " + darkCount);
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, amount * darkCount)));
        }

        this.tickDuration();

    }
}
