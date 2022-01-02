package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IncreaseCostAction extends AbstractGameAction {
    UUID uuid;
    private AbstractCard card = null;
    int amount = 0;

    public IncreaseCostAction(AbstractCard card) {
        this.card = card;// 15
    }// 16

    public IncreaseCostAction(UUID targetUUID, int amount) {
        this.uuid = targetUUID;// 19
        this.amount = amount;// 20
        this.duration = Settings.ACTION_DUR_XFAST;// 21
    }// 22

    public void update() {
        int modifier = 1;
        if (amount > 0) {
            modifier = amount;
        }
        if (this.card == null) {// 26
            for (AbstractCard c : GetAllInBattleInstances.get(uuid)) {
                c.modifyCostForCombat(modifier);
            }
        } else {
            this.card.modifyCostForCombat(modifier);// 31
        }

        this.isDone = true;// 34
    }// 35
}
