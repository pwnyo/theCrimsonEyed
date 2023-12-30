package crimsonEyed.cards.common;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.StormOfSteel;
import com.megacrit.cardcrawl.cards.red.Headbutt;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.common.AttackFromDiscardToHandAction;
import crimsonEyed.actions.common.DrawPileToTopOfDeckAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class PullWires extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(PullWires.class.getSimpleName());
    public static final String IMG = makeCardPath("PullWires.png");// "public static final String IMG = makeCardPath("PullWires.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public PullWires() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        cardsToPreview = new Shiv();
        if (upgraded) {
            cardsToPreview.upgrade();
        }
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new Shiv();
        if (upgraded) {
            c.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c, magicNumber));
        addToBot(new DiscardPileToTopOfDeckAction(p));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            cardsToPreview.upgrade();
            initializeDescription();
        }
    }
}
