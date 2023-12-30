package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Payback() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
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
                if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled")) {
                    count++;
                }
            }
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + count +  cardStrings.EXTENDED_DESCRIPTION[1];
        }
        else {
            rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PaybackAction(m));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}