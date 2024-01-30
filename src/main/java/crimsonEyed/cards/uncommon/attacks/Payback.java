package crimsonEyed.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.PaybackAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.MonsterTargetPatch;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Payback extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Payback.class.getSimpleName());
    public static final String IMG = makeCardPath("Payback.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Payback() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 5;
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
            magicNumber = count;
            rawDescription = cardStrings.DESCRIPTION + (magicNumber == 1 ?
                    cardStrings.EXTENDED_DESCRIPTION[0] : cardStrings.EXTENDED_DESCRIPTION[1]);
            initializeDescription();
        }
        else {
            magicNumber = 0;
            rawDescription = cardStrings.DESCRIPTION;
            initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        magicNumber = 0;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new PaybackAction(m));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}