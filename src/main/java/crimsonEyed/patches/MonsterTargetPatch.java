package crimsonEyed.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(clz = AbstractPlayer.class, method = "updateSingleTargetInput")
public class MonsterTargetPatch {
    public static AbstractMonster hoveredMonster;

    public static void Postfix(AbstractPlayer __player, AbstractMonster ___hoveredMonster) {
        hoveredMonster = ___hoveredMonster;
    }
}
