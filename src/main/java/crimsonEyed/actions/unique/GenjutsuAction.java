package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GenjutsuAction extends AbstractGameAction {
    AbstractMonster target;
    boolean upgraded;

    public GenjutsuAction(AbstractMonster target, int amount, boolean upgraded) {
        this.actionType = ActionType.DEBUFF;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = target;
        this.amount = amount;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
            if (upgraded) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -amount), -amount, true));
                }
            }
            else {
                addToBot(new ApplyPowerAction(target, p, new StrengthPower(target, -amount)));
            }
            this.isDone = true;
        }
    }
}
