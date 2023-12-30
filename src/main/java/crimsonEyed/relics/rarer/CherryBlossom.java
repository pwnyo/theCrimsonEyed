package crimsonEyed.relics.rarer;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
    private static boolean usedThisCombat = false;
    private static int COUNT = 5, HEAL = 5;

    public CherryBlossom() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void atPreBattle() {
        usedThisCombat = false;
        counter = COUNT;
        this.beginPulse();
        this.pulse = true;
    }

    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (usedThisCombat) return;

        counter--;

        if (counter == 0) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new HealAction(p, p, HEAL));
            this.pulse = false;
            usedThisCombat = true;
            this.grayscale = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + COUNT + DESCRIPTIONS[1] + HEAL + DESCRIPTIONS[2];
    }

}
