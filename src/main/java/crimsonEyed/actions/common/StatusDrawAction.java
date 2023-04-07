package crimsonEyed.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;

public class StatusDrawAction extends AbstractGameAction {
    public StatusDrawAction(int multiplier) {
        this.actionType = ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_FAST;
        amount = multiplier;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;

            boolean hasBad = false;
            for (AbstractCard c : p.hand.group) {
                if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                    hasBad = true;
                    break;
                }
            }
            if (hasBad) {
                addToBot(new VFXAction(new DarkSmokePuffEffect(p.hb_x, p.hb_y), 0.1F));
                addToBot(new DrawCardAction(amount));
            }
        }

        this.tickDuration();
        this.isDone = true;
    }
}
