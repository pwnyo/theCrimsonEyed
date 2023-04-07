package crimsonEyed.relics.rarer;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class Kaleidoscope extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(Kaleidoscope.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("kaleidoscope.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("kaleidoscope.png"));

    private static final int AMT = 3;

    public Kaleidoscope() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        flash();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new RelicAboveCreatureAction(m, this));
            this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new LockOnPower(m, AMT), AMT, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMT + DESCRIPTIONS[1];
    }

}
