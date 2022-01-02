package crimsonEyed.relics.commoner;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
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
        this.addToBot(new AbstractGameAction() {// 29
            public void update() {
                if (!CrimsonEye2.this.isActive && AbstractDungeon.player.isBloodied) {
                    CrimsonEye2.this.flash();// 33
                    CrimsonEye2.this.pulse = true;// 34
                    AbstractDungeon.player.addPower(new FocusPower(AbstractDungeon.player, FOCUS_AMT));
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, CrimsonEye2.this));
                    CrimsonEye2.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }

                this.isDone = true;
            }// 41
        });
    }// 44

    public void onBloodied() {
        this.flash();// 48
        this.pulse = true;// 49
        if (!this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, FOCUS_AMT)));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        }

    }// 57

    public void onNotBloodied() {
        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {// 61
            AbstractPlayer p = AbstractDungeon.player;// 62
            this.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, -FOCUS_AMT)));// 63
        }

        this.stopPulse();// 65
        this.isActive = false;// 66
        AbstractDungeon.player.hand.applyPowers();// 67
    }// 68

    public void onVictory() {
        this.pulse = false;// 72
        this.isActive = false;// 73
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FOCUS_AMT + this.DESCRIPTIONS[1];
    }

}
