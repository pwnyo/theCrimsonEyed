package crimsonEyed.relics.boss;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.relics.commoner.CrimsonEye2;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class EternalEye extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(EternalEye.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("eternalEye.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("crimsonEye.png"));
    private static int AMT = 1;

    public EternalEye() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    public void atBattleStart() {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, AMT)));
        this.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, AMT)));
        this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, AMT)));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMT + DESCRIPTIONS[1];
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(CrimsonEye2.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(CrimsonEye2.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(CrimsonEye2.ID);
    }
}
