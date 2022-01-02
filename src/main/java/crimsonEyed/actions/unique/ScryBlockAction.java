package crimsonEyed.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class ScryBlockAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private float startingDuration;
    private int block;

    public ScryBlockAction(int numCards, int block) {
        this.amount = numCards;// 20
        this.block = block;
        if (AbstractDungeon.player.hasRelic("GoldenEye")) {// 22
            AbstractDungeon.player.getRelic("GoldenEye").flash();// 23
            this.amount += 2;// 24
        }

        this.actionType = ActionType.CARD_MANIPULATION;// 27
        this.startingDuration = Settings.ACTION_DUR_FAST;// 28
        this.duration = this.startingDuration;// 29
    }// 30

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {// 34
            this.isDone = true;// 35
        } else {
            Iterator var1;
            AbstractCard c;
            if (this.duration == this.startingDuration) {// 39
                var1 = AbstractDungeon.player.powers.iterator();// 40

                while(var1.hasNext()) {
                    AbstractPower p = (AbstractPower)var1.next();
                    p.onScry();// 41
                }

                if (AbstractDungeon.player.drawPile.isEmpty()) {// 43
                    this.isDone = true;// 44
                    return;// 45
                }

                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);// 47
                if (this.amount != -1) {// 48
                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {// 49
                        tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));// 50 51
                    }
                } else {
                    Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();// 54

                    while(var5.hasNext()) {
                        AbstractCard c2 = (AbstractCard)var5.next();
                        tmpGroup.addToBottom(c2);// 55
                    }
                }

                AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, TEXT[0]);// 58
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 60
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();// 61

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                    addToBot(new GainBlockAction(AbstractDungeon.player, block));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();// 64
            }

            var1 = AbstractDungeon.player.discardPile.group.iterator();// 66

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.triggerOnScry();// 67
            }

            this.tickDuration();// 69
        }
    }// 36 70

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReprogramAction");// 15
        TEXT = uiStrings.TEXT;// 16
    }
}
