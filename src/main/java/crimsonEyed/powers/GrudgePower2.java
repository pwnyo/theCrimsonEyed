package crimsonEyed.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.common.ExhaustFromDiscardTopAction;

public class GrudgePower2 extends AbstractPower {
    public static final String POWER_ID = SasukeMod.makeID("GrudgePower2");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GrudgePower2(AbstractCreature owner, int numCards) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numCards;
        this.type = PowerType.BUFF;

        this.updateDescription();
        loadRegion("hex");
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new ExhaustFromDiscardTopAction(amount));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
}
