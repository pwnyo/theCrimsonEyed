package crimsonEyed.patches;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import crimsonEyed.powers.SeverThePastPower;

@AutoAdd.Ignore
public class SeverThePastPatch {
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile")
    public static class MoveToDiscard {
        public static SpireReturn<Void> Prefix(CardGroup __group, AbstractCard c) {
            if (AbstractDungeon.player.hasPower(SeverThePastPower.POWER_ID)) {
                __group.moveToExhaustPile(c);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class, float.class, float.class
    })
    public static class AddToDiscard {
        public static void Postfix(ShowCardAndAddToDiscardEffect __effect, AbstractCard c) {
            if (AbstractDungeon.player.hasPower(SeverThePastPower.POWER_ID)) {
                AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            }
        }
    }
    @SpirePatch(clz = ShowCardAndAddToDiscardEffect.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class
    })
    public static class AddToDiscard2 {
        public static void Postfix(ShowCardAndAddToDiscardEffect __effect, AbstractCard c) {
            if (AbstractDungeon.player.hasPower(SeverThePastPower.POWER_ID)) {
                AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            }
        }
    }
}
