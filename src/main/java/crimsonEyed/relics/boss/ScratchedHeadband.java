package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.basic.Hatred;
import crimsonEyed.cards.temp.EnduringFlame;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class ScratchedHeadband extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(ScratchedHeadband.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("headband.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("headband.png"));

    public ScratchedHeadband() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }
    public void onEquip() {
        AbstractCard hate = AbstractDungeon.player.masterDeck.findCardById(Hatred.ID);

        if (hate != null) {
            AbstractDungeon.player.masterDeck.removeCard(hate);
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(new EnduringFlame()));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new EnduringFlame(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, false));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
