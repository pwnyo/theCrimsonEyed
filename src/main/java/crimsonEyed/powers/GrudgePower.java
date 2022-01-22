package crimsonEyed.powers;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CombustPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.temp.Resentment;

public class GrudgePower extends AbstractPower {
    public static final String POWER_ID = SasukeMod.makeID("GrudgePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GrudgePower(AbstractCreature owner, int numCards) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numCards;
        this.type = PowerType.BUFF;

        this.updateDescription();
        loadRegion("hex");
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        flash();
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new MakeTempCardInHandAction(new Resentment(), amount));
        }
    }
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
}
