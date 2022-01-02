package crimsonEyed.actions.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MakeTempCardAtTopOfDeckAction extends AbstractGameAction {
    private AbstractCard c;

    public MakeTempCardAtTopOfDeckAction(AbstractCard card) {
        this(card, 1);
    }// 20
    public MakeTempCardAtTopOfDeckAction(AbstractCard card, int amount) {
        this.c = card;
        UnlockTracker.markCardAsSeen(card.cardID);
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {// 45
            this.c.upgrade();
        }
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
    }

    public void update() {
        if (this.duration == this.startDuration) {// 24
            for(int i = 0; i < this.amount; ++i) {// 25
                AbstractCard cardCopy = c.makeStatEquivalentCopy();

                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(cardCopy, false, false));// 68
            }

            this.duration -= Gdx.graphics.getDeltaTime();// 36
        }

        this.tickDuration();// 39
    }// 40
}
