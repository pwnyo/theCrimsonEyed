package crimsonEyed.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PanachePower;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.ShedSkin2Action;

public class ShedSkinPower extends AbstractPower {
    public static final String POWER_ID = SasukeMod.makeID("ShedSkinPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShedSkinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("regrow");

        this.updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        addToBot(new ShedSkin2Action(2));
    }
    public void atEndOfRound() {
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        }
        else {
            //At the end of your next # turns, Exhaust all Status and Curse cards in your hand and heal 2 HP for each.
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
}
