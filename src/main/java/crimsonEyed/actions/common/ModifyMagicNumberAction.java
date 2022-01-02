package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class ModifyMagicNumberAction extends AbstractGameAction {
    UUID uuid;

    public ModifyMagicNumberAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);// 13
        this.actionType = ActionType.CARD_MANIPULATION;// 14
        this.uuid = targetUUID;// 15
    }// 16

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();// 20

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.baseMagicNumber += this.amount;// 21
            if (c.baseDamage < 0) {// 22
                c.baseDamage = 0;// 23
            }
        }

        this.isDone = true;// 26
    }// 27
}
