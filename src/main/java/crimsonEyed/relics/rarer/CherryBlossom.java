package crimsonEyed.relics.rarer;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class CherryBlossom extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(CherryBlossom.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("cherryBlossom.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("cherryBlossom.png"));

    public CherryBlossom() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        counter++;
        if (counter == 5) {
            counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ExhumeAction(false));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 5 + DESCRIPTIONS[1];
    }

}
