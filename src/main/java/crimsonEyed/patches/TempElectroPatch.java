package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.powers.ElectrifyPower;

import static basemod.BaseMod.logger;

public class TempElectroPatch {
    @SpirePatch(clz = Lightning.class, method = "onEvoke")
    public static class Evoke {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(Lightning __instance) {
            if (!AbstractDungeon.player.hasPower("Electro") && AbstractDungeon.player.hasPower(ElectrifyPower.POWER_ID)) {
                logger.info("electrify power evoke");
                AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, __instance.evokeAmount, DamageInfo.DamageType.THORNS), true));
                return SpireReturn.Return();
            }
            else {
                return SpireReturn.Continue();
            }
        }
    }
    @SpirePatch(clz = Lightning.class, method = "onEndOfTurn")
    public static class Passive {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(Lightning __instance) {
            if (!AbstractDungeon.player.hasPower("Electro") && AbstractDungeon.player.hasPower(ElectrifyPower.POWER_ID)) {
                logger.info("electrify power passive");
                AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, __instance.passiveAmount, DamageInfo.DamageType.THORNS), true));
                return SpireReturn.Return();
            }
            else {
                return SpireReturn.Continue();
            }
        }
    }
}
