package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.patches.interfaces.IOnTriggerPassiveListenerPower;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makePowerPath;

public class BlazeBarrierPower extends AbstractPower implements CloneablePowerInterface, IOnTriggerPassiveListenerPower {
    public static final String POWER_ID = SasukeMod.makeID("BlazeBarrierPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public BlazeBarrierPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


    @Override
    public void onTriggerPassive(AbstractOrb o, boolean atStart) {
        SasukeMod.logger.info("received " + o + ", " + atStart);
        if (!atStart && o instanceof Dark) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new FlameBarrierPower(owner, amount)));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BlazeBarrierPower(owner, amount);
    }

}