package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.ArrayList;

public class SixPathsAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public SixPathsAction(int potency) {
        this.actionType = ActionType.CARD_MANIPULATION;// 19
        this.duration = Settings.ACTION_DUR_FAST;// 20
        amount = potency;
    }// 22

    public void update() {
        // 53
        if (this.duration == Settings.ACTION_DUR_FAST) {// 48
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], false);// 49
            this.tickDuration();// 53
        } else {
            if (!this.retrieveCard) {// 57
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {// 58
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 59
                    AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();// 60
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {// 62
                        disCard.upgrade();// 63
                        disCard2.upgrade();// 64
                    }

                    disCard.setCostForTurn(0);// 67
                    disCard2.setCostForTurn(0);// 68
                    disCard.current_x = -1000.0F * Settings.xScale;// 70
                    disCard2.current_x = -1000.0F * Settings.xScale + AbstractCard.IMG_HEIGHT_S;// 71
                    if (this.amount == 1) {// 73
                        if (AbstractDungeon.player.hand.size() < 10) {// 74
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 75
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 78
                        }

                        disCard2 = null;// 81
                    } else if (AbstractDungeon.player.hand.size() + this.amount <= 10) {// 83
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 84
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 89
                    } else if (AbstractDungeon.player.hand.size() == 9) {// 95
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 96
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList();// 57
        for (AbstractCard.CardColor color : AbstractCard.CardColor.values()) {
            if (!(color == AbstractCard.CardColor.RED || color == AbstractCard.CardColor.BLUE || color == AbstractCard.CardColor.GREEN
                    || color == AbstractCard.CardColor.PURPLE || color == AbstractCard.CardColor.COLORLESS))
                continue;

            boolean dupe = false;

            do {
                AbstractCard tmp = TheCrimsonEyed.getColoredCard(CardRarity.RARE, color);
                for (AbstractCard c : derp) {
                    if (c.cardID.equals(tmp.cardID)) {// 76
                        dupe = true;// 77
                        break;// 78
                    }
                }

                if (!dupe) {
                    derp.add(tmp.makeCopy());
                }
            } while (dupe);
        }

        return derp;
    }
}
