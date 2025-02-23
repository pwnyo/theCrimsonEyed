package crimsonEyed.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;

public class UniversalPullPower extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = SasukeMod.makeID("SpaceTimePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractCard card;

    public UniversalPullPower(AbstractCreature owner, int wait, AbstractCard card) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = wait;
        this.type = PowerType.BUFF;
        this.card = card.makeStatEquivalentCopy();
        this.card.resetAttributes();
        this.loadRegion("establishment");

        this.updateDescription();
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
                    FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[2];
        }
        else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] +
                    FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        amount--;
        if (amount <= 0) {
            this.addToBot(new MakeTempCardInHandAction(this.card));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }
}