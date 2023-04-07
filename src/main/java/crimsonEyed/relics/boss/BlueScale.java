package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.relics.WarPaint;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.basic.Hatred;
import crimsonEyed.cards.temp.EnduringFlame;
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

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Hatred(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        ++AbstractDungeon.player.energy.energyMaster;
    }
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
