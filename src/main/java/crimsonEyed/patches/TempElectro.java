package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.powers.SpitePower;

import static basemod.BaseMod.logger;

@SpirePatch(clz = Lightning.class, method = "onEvoke")
public class TempElectro {
    //@SpirePrefixPatch
    public static void Prefix(Lightning __instance) {
        try {
            if (!AbstractDungeon.player.hasPower("Electro") && AbstractDungeon.player.hasPower(SpitePower.POWER_ID)) {
                logger.info("electrify power running");
                AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, __instance.evokeAmount, DamageInfo.DamageType.THORNS), true));// 64
            }
        }
        catch (NullPointerException np) {
            logger.info("electrify power failed");
        }
    }
}
