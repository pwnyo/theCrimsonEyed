package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class DragonFlameAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private int multiplier;

    public DragonFlameAction(AbstractMonster m, int baseDamage, int multiplier) {
        this.amount = baseDamage;
        this.multiplier = multiplier;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;

        p = AbstractDungeon.player;
        this.m = m;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int count = 0;

            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while (var1.hasNext()) {
                AbstractCard c = (AbstractCard) var1.next();
                if (c.type != AbstractCard.CardType.SKILL) {
                    count++;
                    this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }

            addToBot(new DamageAction(m, new DamageInfo(p, amount + count * multiplier, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));

            this.isDone = true;
        }
    }
}
