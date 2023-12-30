package crimsonEyed.relics.rarer;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class NohMask extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(NohMask.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("nohMask.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("nohMask.png"));

    public NohMask() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public static boolean shouldUseMaskDesc() {
        return CardCrawlGame.dungeon != null && AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(NohMask.ID)
                && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
}