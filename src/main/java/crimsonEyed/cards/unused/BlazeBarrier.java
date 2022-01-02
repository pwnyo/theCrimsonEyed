package crimsonEyed.cards.unused;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.powers.BlazeBarrierPower;

import static crimsonEyed.SasukeMod.makeCardPath;

@AutoAdd.Ignore
public class BlazeBarrier extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(BlazeBarrier.class.getSimpleName());
    public static final String IMG = makeCardPath("BlazeBarrier.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 0;
    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public BlazeBarrier() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = 0;
    }

    void countDark() {
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Dark) {
                ++baseMagicNumber2;
            }
        }
        magicNumber2 = baseMagicNumber2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BlazeBarrierPower(p, magicNumber * magicNumber2)));
    }

    public void applyPowers() {
        super.applyPowers();// 52
        this.baseMagicNumber2 = 0;// 54
        this.magicNumber2 = 0;// 55
        countDark();

        if (this.baseMagicNumber2 > 0) {// 62
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 63
            this.initializeDescription();// 64
        }

    }// 66

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;// 70
        this.initializeDescription();// 71
    }// 72

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 76
        if (this.baseMagicNumber2 > 0) {// 77
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 78
        }

        this.initializeDescription();// 80
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
