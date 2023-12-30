package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.UchiwaAction;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class Uchiwa extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(Uchiwa.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("uchiwa.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("uchiwa.png"));
    private boolean activated = false;

    public Uchiwa() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public void atBattleStartPreDraw() {
        activated = false;
    }
    public void atTurnStartPostDraw() {
        if (!this.activated) {
            this.activated = true;

            addToBot(new UchiwaAction(this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}