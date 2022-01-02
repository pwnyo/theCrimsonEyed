package crimsonEyed.relics.rarer;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class StoneTablet extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(StoneTablet.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("stoneTablet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("stoneTablet.png"));

    public StoneTablet() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
        pulse = false;
    }

    public void atTurnStart() {
        if (this.pulse) {
            this.pulse = false;
            this.flash();
            addToBot(new ScryAction(2));
        }

    }

    public void wasHPLost(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            this.flash();
            if (!this.pulse) {
                this.beginPulse();
                this.pulse = true;
            }
        }
    }

    public void onVictory() {
        this.pulse = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
    }
}
