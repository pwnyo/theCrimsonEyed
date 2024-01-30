package crimsonEyed.actions.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.CollectorStakeEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;

public class TsukuyomiCurseEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private int count;
    private int max;
    private float stakeTimer = 0.0F;

    public TsukuyomiCurseEffect(float x, float y, int debuffs) {
        this.x = x;
        this.y = y;
        this.max = debuffs;
    }// 21

    public void update() {
        this.stakeTimer -= Gdx.graphics.getDeltaTime();
        if (this.stakeTimer < 0.0F) {
            if (this.count == 0) {
                CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.5F);
                AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), Math.min(0.8F, max * 0.2F)));
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.0F, 1.0F, 0.7F)));
            }

            AbstractDungeon.effectsQueue.add(new CollectorStakeEffect(this.x + MathUtils.random(-50.0F, 50.0F) * Settings.scale, this.y + MathUtils.random(-60.0F, 60.0F) * Settings.scale));
            this.stakeTimer = 0.04F;
            count++;
            if (this.count == max * 4) {
                this.isDone = true;
            }
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
