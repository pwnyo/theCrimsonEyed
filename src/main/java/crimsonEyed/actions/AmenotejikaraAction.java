package crimsonEyed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class AmenotejikaraAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<AbstractCard> finds = new ArrayList();

    public AmenotejikaraAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator iterator;
        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.drawPile.size() == 1) {
                if (((AbstractCard)this.p.drawPile.group.get(0)).cardID.equals("Exhume")) {
                    this.isDone = true;
                } else {
                    AbstractCard c = this.p.drawPile.getTopCard();
                    c.unfadeOut();
                    this.p.hand.addToHand(c);
                    if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                        c.setCostForTurn(-9);
                    }

                    this.p.drawPile.removeCard(c);

                    c.unhover();
                    c.fadingOut = false;
                    this.isDone = true;
                }
            } else {
                iterator = this.p.drawPile.group.iterator();

                while(iterator.hasNext()) {
                    derp = (AbstractCard)iterator.next();
                    derp.stopGlowing();
                    derp.unhover();
                    derp.unfadeOut();
                }

                iterator = this.p.drawPile.group.iterator();

                while(iterator.hasNext()) {
                    derp = (AbstractCard)iterator.next();
                    if (derp.cardID.equals("Amenotejikara")) {
                        iterator.remove();
                        this.finds.add(derp);
                    }
                }

                if (this.p.drawPile.isEmpty()) {
                    this.p.drawPile.group.addAll(this.finds);
                    this.finds.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.drawPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); derp.unhover()) {
                    derp = (AbstractCard)iterator.next();
                    this.p.hand.addToHand(derp);
                    if (AbstractDungeon.player.hasPower("Corruption") && derp.type == AbstractCard.CardType.SKILL) {
                        derp.setCostForTurn(-9);
                    }

                    this.p.drawPile.removeCard(derp);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.drawPile.group.addAll(this.finds);
                this.finds.clear();

                for(iterator = this.p.drawPile.group.iterator(); iterator.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard)iterator.next();
                    derp.unhover();
                    derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }
}
