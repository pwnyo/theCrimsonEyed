package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.chop.Dice;
import crimsonEyed.cards.temp.chop.Rend;
import crimsonEyed.cards.temp.chop.Slash;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Chop2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Chop2.class.getSimpleName());
    public static final String IMG = makeCardPath("Chop.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;

    // /STAT DECLARATION/


    public Chop2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 20;
        baseMagicNumber = magicNumber = 10;
        baseMagicNumber2 = magicNumber2 = 3;
        checkMaskDesc();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new Slash(m));
        stanceChoices.add(new Rend(m));
        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Dice(m));
        }
        if (upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }
    public void applyPowers() {
        int normalDmg = baseDamage;

        this.baseDamage = baseMagicNumber;
        super.applyPowers();
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;

        isMultiDamage = true;
        this.baseDamage = baseMagicNumber2;
        super.applyPowers();
        this.magicNumber2 = this.damage;
        this.isMagicNumber2Modified = this.isDamageModified;

        isMultiDamage = false;
        this.baseDamage = normalDmg;
        super.applyPowers();
        checkMaskDesc();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        isMultiDamage = true;
        int normalDmg = baseDamage;

        //aoe damage
        this.baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo);
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;

        isMultiDamage = false;

        //random damage
        this.baseDamage = baseMagicNumber2;
        super.calculateCardDamage(mo);
        this.magicNumber2 = this.damage;
        this.isMagicNumber2Modified = this.isDamageModified;

        this.baseDamage = normalDmg;
        super.calculateCardDamage(mo);
        checkMaskDesc();
    }
    void checkMaskDesc() {
        if (NohMask.shouldUseMaskDesc()) {
            rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        }
        else {
            rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(5);
            upgradeSecondMagicNumber(1);
            checkMaskDesc();
            initializeDescription();
        }
    }
}