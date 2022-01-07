package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;

public class LightningSpeedPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SasukeMod.makeID("LightningSpeedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int channelCount;

    public LightningSpeedPower(AbstractCreature owner, int channelCount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 3;
        this.channelCount = channelCount;
        this.loadRegion("burst");

        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + channelCount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + channelCount + DESCRIPTIONS[2];
        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.channelCount += 1;
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            return;
        }
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = 3;

            for (int i = 0; i < channelCount; i++) {
                addToBot(new ChannelAction(new Lightning()));
            }
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = 3;
        this.updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new LightningSpeedPower(owner, amount);
    }
}