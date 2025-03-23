package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    private boolean triggeredThisTurn = false;

    public BlueScale() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!this.triggeredThisTurn && (drawnCard.type == AbstractCard.CardType.CURSE || drawnCard.type == AbstractCard.CardType.STATUS)) {
            this.triggeredThisTurn = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
