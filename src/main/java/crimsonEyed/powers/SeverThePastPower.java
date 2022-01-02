package crimsonEyed.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makePowerPath;

public class SeverThePastPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SasukeMod.makeID("SeverThePastPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public SeverThePastPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    /*
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[B] ");
        }

        sb.append(LocalizedStrings.PERIOD);
        sb.append(powerStrings.DESCRIPTIONS[1]);
        this.description = sb.toString();
    }// 31

    public void atStartOfTurn() {
        addToBot(new GainEnergyAction(amount));
        addToBot(new DrawCardAction(amount));
        flash();
    }
    */

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        //addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player.exhaustPile.size()));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SeverThePastPower(owner);
    }
}