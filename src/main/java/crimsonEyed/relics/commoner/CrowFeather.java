package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class CrowFeather extends CustomRelic implements OnApplyPowerRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(CrowFeather.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("crowFeather.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("crowFeather.png"));

    private static final int BLOCK = 2;
    public CrowFeather() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && target instanceof AbstractMonster && source instanceof AbstractPlayer) {
            flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK, true));
        }
        return true;
    }
}
