package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class RandomLockOnAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private int numTimes;
    private int amount;

    public RandomLockOnAction(AbstractCreature target, int amount, int numTimes) {
        this.target = target;// 19
        this.actionType = ActionType.DEBUFF;// 20
        this.duration = 0.01F;// 21
        this.numTimes = numTimes;// 22
        this.amount = amount;// 23
    }// 24

    public void update() {
        if (this.target == null) {// 28
            this.isDone = true;// 29
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 33
            AbstractDungeon.actionManager.clearPostCombatActions();// 34
            this.isDone = true;// 35
        } else {
            if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 40
                --this.numTimes;// 41
                AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);// 42
                this.addToTop(new RandomLockOnAction(randomMonster, this.amount, this.numTimes));// 46
            }

            if (this.target.currentHealth > 0) {// 53
                this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new LockOnPower(target, amount), amount, true, AttackEffect.POISON));// 54
                this.addToTop(new WaitAction(0.1F));// 62
            }

            this.isDone = true;// 65
        }
    }// 30 36 66
}
