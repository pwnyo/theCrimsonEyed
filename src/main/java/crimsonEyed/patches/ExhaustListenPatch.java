package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crimsonEyed.patches.interfaces.IOnExhaustListenerCard;

@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
public class ExhaustListenPatch {
    @SpirePostfixPatch
    public static void Postfix(CardGroup __group, AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof IOnExhaustListenerCard) {
                ((IOnExhaustListenerCard) c).didExhaust();
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c instanceof IOnExhaustListenerCard) {
                ((IOnExhaustListenerCard) c).didExhaust();
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c instanceof IOnExhaustListenerCard) {
                ((IOnExhaustListenerCard) c).didExhaust();
            }
        }
    }
}
