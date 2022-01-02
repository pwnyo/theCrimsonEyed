package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class BetterDiscoveryAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean returnColorless = false;
    private AbstractCard.CardType cardType = null;
    private int choices;

    public BetterDiscoveryAction(int amount, int choices) {
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FAST;// 21
        this.amount = amount;// 22
        if (choices > 0)
            this.choices = choices;
        else
            this.choices = 3;
    }
    public BetterDiscoveryAction(boolean oneOfEach) {
        this.actionType = ActionType.CARD_MANIPULATION;// 20
        this.duration = Settings.ACTION_DUR_FAST;// 21
        this.amount = 5;
    }

    public void update() {
        ArrayList generatedCards;
        if (this.returnColorless) {// 42
            generatedCards = this.generateColorlessCardChoices();// 43
        } else {
            generatedCards = this.generateCardChoices(this.cardType);// 45
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {// 48
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], this.cardType != null);// 49
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
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 101
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 107
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 112
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;// 121
                }

                this.retrieveCard = true;// 123
            }

            this.tickDuration();// 126
        }
    }// 54 127

    private ArrayList<AbstractCard> generateColorlessCardChoices() {
        ArrayList derp = new ArrayList();// 130

        while(derp.size() != 3) {// 132
            boolean dupe = false;// 133
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();// 135
            Iterator var4 = derp.iterator();// 136

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                if (c.cardID.equals(tmp.cardID)) {// 137
                    dupe = true;// 138
                    break;// 139
                }
            }

            if (!dupe) {// 142
                derp.add(tmp.makeCopy());// 143
            }
        }

        return derp;// 147
    }

    private ArrayList<AbstractCard> generateCardChoices(AbstractCard.CardType type) {
        ArrayList derp = new ArrayList();// 151

        while(derp.size() != choices) {// 153
            boolean dupe = false;// 154
            AbstractCard tmp = null;// 155
            if (type == null) {// 156
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat();// 157
            } else {
                tmp = AbstractDungeon.returnTrulyRandomCardInCombat(type);// 159
            }

            Iterator var5 = derp.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();// 161
                if (c.cardID.equals(tmp.cardID)) {// 162
                    dupe = true;// 163
                    break;// 164
                }
            }

            if (!dupe) {// 167
                derp.add(tmp.makeCopy());// 168
            }
        }

        return derp;// 172
    }
}
