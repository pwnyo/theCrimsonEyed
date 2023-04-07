package crimsonEyed.patches;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.patches.interfaces.IOnAddToHandListenerPower;

@AutoAdd.Ignore
@SpirePatch(clz = CardGroup.class, method = "addToHand")
public class RevolutionPatch {
    @SpirePostfixPatch
    public static void Postfix(CardGroup __group, AbstractCard card) {
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof IOnAddToHandListenerPower) {
                ((IOnAddToHandListenerPower) pow).onAddToHand(card);
            }
        }
    }
}

