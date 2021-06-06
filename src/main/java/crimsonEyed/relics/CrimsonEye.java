package crimsonEyed.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.RedMask;
import crimsonEyed.DefaultMod;
import crimsonEyed.util.TextureLoader;
import sun.security.util.Debug;

import java.util.Iterator;

import static crimsonEyed.DefaultMod.makeRelicOutlinePath;
import static crimsonEyed.DefaultMod.makeRelicPath;

public class CrimsonEye extends CustomRelic {
    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CrimsonEye");

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
            this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new LockOnPower(mo, 2), 1, true));// 28
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
