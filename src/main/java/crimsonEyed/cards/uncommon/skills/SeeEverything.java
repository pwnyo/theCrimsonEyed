package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.Iterator;

import static crimsonEyed.SasukeMod.makeCardPath;

public class SeeEverything extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(SeeEverything.class.getSimpleName());
    public static final String IMG = makeCardPath("SeeEverything.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = -1;
    private static final int BONUS = 1;

    // /STAT DECLARATION/


    public SeeEverything() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = EnergyPanel.totalCount;

        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (upgraded) {
            effect += BONUS;
        }

        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {

            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var3.hasNext()) {
                m = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(m, p, new LockOnPower(m, effect), effect, true, AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, effect, false), effect, true, AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, effect, false), effect, true, AbstractGameAction.AttackEffect.NONE));
            }
            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
