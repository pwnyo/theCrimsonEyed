package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class BlueScale extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(BlueScale.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("blueScale.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("blueScale.png"));
    private static final int CURSES = 3;

    public BlueScale() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    // Flash at the start of Battle.
    @Override
    public void atTurnStart() {
        if (hasEnoughCurses()) {
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainEnergyAction(1));
        }
    }
    boolean hasEnoughCurses() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE && !(c instanceof AscendersBane)) {
                count++;
            }
        }
        return (count >= CURSES);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CURSES + DESCRIPTIONS[1];
    }
}
