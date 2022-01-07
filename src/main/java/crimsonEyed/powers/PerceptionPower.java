package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makePowerPath;

public class PerceptionPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SasukeMod.makeID("PerceptionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("perception84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("perception32.png"));

    public PerceptionPower(AbstractCreature owner, int amount) {
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
        if (this.amount == 1) {// 36
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];// 37
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];// 39
        }
    }

    @Override
    public void onScry() {
        flash();
        addToBot(new DrawCardAction(amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new PerceptionPower(owner, amount);
    }
}