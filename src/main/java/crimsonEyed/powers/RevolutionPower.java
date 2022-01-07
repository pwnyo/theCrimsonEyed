package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import crimsonEyed.SasukeMod;

public class RevolutionPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SasukeMod.makeID("RevolutionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RevolutionPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("corruption");
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE && !this.owner.hasPower("No Draw")) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RevolutionPower(owner, amount);
    }
}