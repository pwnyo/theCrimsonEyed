package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import java.util.Iterator;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class CrimsonEye extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID("CrimsonEye");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public CrimsonEye() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        flash();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 26

        while(var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var1.next();
            this.addToBot(new RelicAboveCreatureAction(mo, this));// 27
            this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new LockOnPower(mo, 1), 1, true));// 28
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
