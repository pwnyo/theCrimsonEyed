package crimsonEyed.powers;

import basemod.devcommands.draw.Draw;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.GremlinHorn;
import crimsonEyed.SasukeMod;
import crimsonEyed.patches.interfaces.IOnMonsterDeathListenerPower;

public class SpitePower extends AbstractPower implements CloneablePowerInterface, IOnMonsterDeathListenerPower {
    public static final String POWER_ID = SasukeMod.makeID("SpitePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SpitePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.updateDescription();
        loadRegion("sadistic");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SpitePower(owner, amount);
    }

    @Override
    public void onMonsterDeath(AbstractMonster monster) {
        flash();
        addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(1));
    }
}
