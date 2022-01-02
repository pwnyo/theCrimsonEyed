package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.powers.RevolutionPower;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Revolution extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Revolution.class.getSimpleName());public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Vengeance.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public Revolution() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RevolutionPower(p, magicNumber)));
        /*
        int count = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                count++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                count++;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS) {
                count++;
            }
        }
        if (count > 0)
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, count * magicNumber)));
        */
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}