package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crimsonEyed.patches.interfaces.*;

import java.util.Iterator;

public class OrbListenerPatch {
    @SpirePatch(clz = AbstractPower.class, method = "onChannel")
    public static class OnChannel {
        @SpirePostfixPatch
        public static void Postfix(AbstractPower __power, AbstractOrb orb) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractCard c : p.hand.group) {
                if (c instanceof IOnChannelListenerCard) {
                    IOnChannelListenerCard listener = (IOnChannelListenerCard) c;
                    listener.onChannel(orb);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPower.class, method = "onEvokeOrb")
    public static class OnEvoke {
        @SpirePostfixPatch
        public static void Postfix(AbstractPower __power, AbstractOrb orb) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractCard c : p.hand.group) {
                if (c instanceof IOnEvokeListenerCard) {
                    IOnEvokeListenerCard listener = (IOnEvokeListenerCard) c;
                    listener.onEvoke(orb);
                }
            }
        }
    }
    @SpirePatch(clz = AbstractPlayer.class, method = "increaseMaxOrbSlots")
    public static class OnGainOrbSlot {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __player, int amount) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractCard c : p.hand.group) {
                if (c instanceof IOnGainOrbSlotListenerCard) {
                    IOnGainOrbSlotListenerCard listener = (IOnGainOrbSlotListenerCard) c;
                    listener.onGainOrbSlot();
                }
            }
        }
    }
    @SpirePatch(clz = AbstractPlayer.class, method = "decreaseMaxOrbSlots")
    public static class OnLoseOrbSlot {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __player, int amount) {
            AbstractPlayer p = AbstractDungeon.player;

            Iterator iterator = p.hand.group.iterator();
            AbstractCard c;
            while (iterator.hasNext()) {
                c = (AbstractCard) iterator.next();
                if (c instanceof IOnLoseOrbSlotListenerCard) {
                    IOnLoseOrbSlotListenerCard listener = (IOnLoseOrbSlotListenerCard) c;
                    listener.onLoseOrbSlot();
                }
            }
        }
    }
    @SpirePatch(clz = AbstractOrb.class, method = "onStartOfTurn")
    public static class OnTriggerPassiveStart {
        @SpirePostfixPatch
        public static void Postfix(AbstractOrb __orb) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractPower po : p.powers) {
                if (po instanceof IOnTriggerPassiveListenerPower) {
                    IOnTriggerPassiveListenerPower listener = (IOnTriggerPassiveListenerPower) po;
                    listener.onTriggerPassive(__orb, true);
                }
            }
        }
    }
    @SpirePatch(clz = Dark.class, method = "onEndOfTurn")
    public static class OnTriggerPassiveEnd {
        @SpirePostfixPatch
        public static void Postfix(Dark __orb) {
            AbstractPlayer p = AbstractDungeon.player;

            for (AbstractPower po : p.powers) {
                if (po instanceof IOnTriggerPassiveListenerPower) {
                    IOnTriggerPassiveListenerPower listener = (IOnTriggerPassiveListenerPower) po;
                    listener.onTriggerPassive(__orb, false);
                }
            }
        }
    }
}
