package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;

public class OneStepAheadPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SasukeMod.makeID("OneStepAheadPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public OneStepAheadPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.updateDescription();
        loadRegion("retain");
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[2];
        }
        else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (!AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium")) {
                flash();
                this.addToBot(new RetainCardsAction(this.owner, 1));// 38
            }
            if (amount <= 1) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
            else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new OneStepAheadPower(owner, amount);
    }
}