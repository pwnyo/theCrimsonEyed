package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class PaperSnek extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(PaperSnek.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("paperSnek.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("paperSnek.png"));

    public PaperSnek() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}