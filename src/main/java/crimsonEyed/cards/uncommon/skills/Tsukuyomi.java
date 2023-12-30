package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.TsukuyomiAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.MonsterTargetPatch;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Tsukuyomi extends AbstractDynamicCard {

    public static final String ID = SasukeMod.makeID(Tsukuyomi.class.getSimpleName());
    public static final String IMG = makeCardPath("Tsukuyomi.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 3;

    public Tsukuyomi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = 2;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        checkDebuffs();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        checkDebuffs();
    }

    void checkDebuffs() {
        AbstractMonster m = MonsterTargetPatch.hoveredMonster;
        if (m != null) {
            int count = 0;
            for (AbstractPower pow : m.powers) {
                if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled") && !pow.ID.equals(VulnerablePower.POWER_ID)) {
                    count++;
                }
            }
            damage = count;
            rawDescription = cardStrings.DESCRIPTION + (damage == 1 ?
                    cardStrings.EXTENDED_DESCRIPTION[0] : cardStrings.EXTENDED_DESCRIPTION[1]);
            initializeDescription();
        }
        else {
            damage = 0;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        damage = 0;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 2));
        addToBot(new TsukuyomiAction(m, magicNumber));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber2, false)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeSecondMagicNumber(1);
            initializeDescription();
        }
    }
}
