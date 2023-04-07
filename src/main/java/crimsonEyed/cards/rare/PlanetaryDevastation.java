package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.PlanetaryDevastationAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class PlanetaryDevastation extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(PlanetaryDevastation.class.getSimpleName());
    public static final String IMG = makeCardPath("PlanetaryDevastation.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    // /STAT DECLARATION/


    public PlanetaryDevastation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlanetaryDevastationAction());
        addToBot(new IncreaseMaxOrbAction(3));
    }
    static int countDebuffs() {
        int count = 0;
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower pow : mo.powers) {
                if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled")) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    public void applyPowers() {
        super.applyPowers();// 52
        baseMagicNumber = magicNumber = countDebuffs();

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
        this.initializeDescription();

    }// 66

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.DESCRIPTION;
        }

        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}