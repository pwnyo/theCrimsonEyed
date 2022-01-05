package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class UpgradableDiscoveryAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private CardType cardType = null;
    private boolean upgrade = false;
    private int choiceCount = 3;

    public UpgradableDiscoveryAction(int choices, boolean useUpgrades) {
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FAST;// 21
        this.amount = 1;
        if (choices > 1)
            this.choiceCount = choices;
        this.upgrade = useUpgrades;
    }

    public UpgradableDiscoveryAction(CardType type, int choices, boolean useUpgrades) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
        if (choices > 1)
            this.choiceCount = choices;
        this.cardType = type;
        this.upgrade = useUpgrades;
    }

    public void update() {
        ArrayList<AbstractCard> generatedCards = this.generateCardChoices(this.cardType);// 45

        if (this.duration == Settings.ACTION_DUR_FAST) {// 48
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], this.cardType != null);// 49
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
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 75
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 78
                        }

                        disCard2 = null;// 81
                    } else if (AbstractDungeon.player.hand.size() + this.amount <= 10) {// 83
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 84
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 89
                    } else if (AbstractDungeon.player.hand.size() == 9) {// 95
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 96
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

        }
        this.tickDuration();// 53
    }// 54 127

    private ArrayList<AbstractCard> generateCardChoices(CardType type) {
        ArrayList<AbstractCard> derp = new ArrayList<>();

        while(derp.size() != choiceCount) {
            boolean dupe = false;
            AbstractCard tmp = null;
            if (type == null) {
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            } else {
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(type);
            }

            Iterator var5 = derp.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                if (upgrade) {
                    AbstractCard up = tmp.makeCopy();
                    up.upgrade();
                    derp.add(up);
                }
                else {
                    derp.add(tmp.makeCopy());
                }
            }
        }

        return derp;
    }
}

