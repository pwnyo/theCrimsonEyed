package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SpiteAction extends AbstractGameAction {
    private DamageInfo info;

    public SpiteAction(AbstractCreature target, DamageInfo info, int amount) {
        this.info = info;// 20
        this.setValues(target, info);// 21
        this.amount = amount;// 22
        this.actionType = ActionType.DAMAGE;// 23
        this.duration = Settings.ACTION_DUR_FASTER;
    }// 25

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {// 29 30
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));// 31
            this.target.damage(this.info);// 33
            if (this.target.isDying || this.target.currentHealth <= 0) {
                AbstractPlayer p = AbstractDungeon.player;
                addToBot(new HealAction(p, p, amount));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 39
                AbstractDungeon.actionManager.clearPostCombatActions();// 40
            }
        }

        this.tickDuration();// 45
    }// 46
}