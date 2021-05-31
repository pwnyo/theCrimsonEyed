package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;

@SpirePatch(clz = Lightning.class, method = "onEvoke")
public class TempElectro {
    @SpirePrefixPatch
    public static void Prefix(Lightning __instance) {
        if (!AbstractDungeon.player.hasPower("Electro") && AbstractDungeon.player.hasPower("ElectrifyPower")) {// 63
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, __instance.evokeAmount, DamageInfo.DamageType.THORNS), true));// 64
        }
    }
}
