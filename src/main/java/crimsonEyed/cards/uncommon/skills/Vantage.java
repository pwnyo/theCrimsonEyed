package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.ScryBlockAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Vantage extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Vantage.class.getSimpleName());
    public static final String IMG = makeCardPath("Vantage.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Vantage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = 3;
        baseMagicNumber = magicNumber = 3;
        isInnate = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryBlockAction(magicNumber, block));
        /*
        baseMagicNumber = 0;
        countHand();
        addToBot(new GainBlockAction(p, magicNumber));
        addToBot(new ScryAction(magicNumber));*/
    }

    /*
    void countHand() {
        baseMagicNumber += AbstractDungeon.player.hand.size();
        if (upgraded)
            baseMagicNumber += 3;
        magicNumber = baseMagicNumber;
    }

    public void applyPowers() {
        super.applyPowers();

        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        countHand();

        if (baseMagicNumber > 0) {
            if (!upgraded) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            }
            else {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            }
            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        if (!upgraded) {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        else {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (baseMagicNumber > 0) {
            if (!upgraded) {
                this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            }
            else {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            }
        }

        this.initializeDescription();
    }
    */

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
