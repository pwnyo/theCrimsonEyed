package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import crimsonEyed.SasukeMod;

import java.util.Iterator;
import java.util.UUID;

public class SkeletonSwordAction extends AbstractGameAction {
    private DamageInfo info;
    private UUID uuid;

    public SkeletonSwordAction(AbstractCreature target, DamageInfo info, int amount, UUID targetUUID) {
        this.info = info;// 20
        this.setValues(target, info);// 21
        this.amount = amount;// 22
        this.actionType = ActionType.DAMAGE;// 23
        this.duration = Settings.ACTION_DUR_FASTER;// 24
        this.uuid = targetUUID;
    }// 25

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {// 29 30
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HEAVY));// 31
            this.target.damage(this.info);// 33
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {// 35
                SasukeMod.logger.info("skeleton killed an enemy!");
                Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();// 36

                AbstractCard c;
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (c.uuid.equals(this.uuid)) {
                        c.misc += amount;
                        c.applyPowers();
                        c.baseMagicNumber = c.misc;
                        c.isMagicNumberModified = false;
                    }
                }

                for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseMagicNumber = c.misc) {// 44 47
                    c = (AbstractCard)var1.next();
                    c.misc += this.amount;// 45
                    c.applyPowers();// 46
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {// 39
                AbstractDungeon.actionManager.clearPostCombatActions();// 40
            }
        }

        this.tickDuration();// 45
    }// 46
}
