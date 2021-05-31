package crimsonEyed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import crimsonEyed.powers.CommonPower;

import java.util.Iterator;

public class LightningImpulseAction extends AbstractGameAction {
    public LightningImpulseAction() {
        this.duration = Settings.ACTION_DUR_FAST;// 14
    }// 15

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty()) {// 19 20
            Iterator var1 = AbstractDungeon.player.orbs.iterator();// 21

            while(var1.hasNext()) {
                AbstractOrb o = (AbstractOrb)var1.next();
                if (o instanceof Lightning) {// 22
                    o.onStartOfTurn();// 23
                    o.onEndOfTurn();// 24
                }
            }

            if (AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) && AbstractDungeon.player.orbs.get(0) instanceof Lightning) {// 28 30
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();// 31
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();// 32
            }
        }

        this.tickDuration();// 38
    }// 39
}
