package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.EssenceOfDarkness;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.Damaru;
import com.megacrit.cardcrawl.relics.DataDisk;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.util.TextureLoader;

import static crimsonEyed.SasukeMod.makeRelicOutlinePath;
import static crimsonEyed.SasukeMod.makeRelicPath;

public class CrimsonEye2 extends CustomRelic {
    // ID, images, text.
    public static final String ID = SasukeMod.makeID(CrimsonEye2.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("crimsonEye.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("crimsonEye.png"));
    private static final int FOCUS_AMT = 2;
    private boolean isActive = false;

    public CrimsonEye2() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    public void atBattleStart() {
        this.isActive = false;
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (!CrimsonEye2.this.isActive && AbstractDungeon.player.isBloodied) {
                    CrimsonEye2.this.flash();
                    CrimsonEye2.this.pulse = true;
                    AbstractDungeon.player.addPower(new FocusPower(AbstractDungeon.player, FOCUS_AMT));
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, CrimsonEye2.this));
                    CrimsonEye2.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }

                this.isDone = true;
            }
        });
    }

    public void onBloodied() {
        this.flash();
        this.pulse = true;
        if (!this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, FOCUS_AMT)));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        }

    }

    public void onNotBloodied() {
        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, -FOCUS_AMT)));
        }

        this.stopPulse();
        this.isActive = false;
        AbstractDungeon.player.hand.applyPowers();
    }

    public void onVictory() {
        this.pulse = false;
        this.isActive = false;// 73
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FOCUS_AMT + this.DESCRIPTIONS[1];
    }

}
