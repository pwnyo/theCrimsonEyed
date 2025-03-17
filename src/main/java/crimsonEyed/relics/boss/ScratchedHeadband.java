package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.basic.Hatred;
import crimsonEyed.cards.temp.EnduringFlame;
import crimsonEyed.util.TextureLoader;

import java.util.ArrayList;

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
        ArrayList<AbstractCard> hates = new ArrayList<>();

        float displayCount = 0.0F;

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(Hatred.ID)) {
                hates.add(c);
            }
        }
        for (AbstractCard c : hates) {
            AbstractCard hope = new EnduringFlame();
            hope.upgrade();

            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)Settings.WIDTH / 3.0F + displayCount, (float)Settings.HEIGHT / 3.0F));
            displayCount += (float)Settings.WIDTH / 3.0F;
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(hope, (float)Settings.WIDTH / 3.0F + displayCount, (float)Settings.HEIGHT / 3.0F));
        }
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
