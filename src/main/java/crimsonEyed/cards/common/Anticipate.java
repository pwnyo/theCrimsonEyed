package crimsonEyed.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.anticipate.Condition;
import crimsonEyed.cards.temp.anticipate.React;
import crimsonEyed.cards.temp.anticipate.Read;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Anticipate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Anticipate.class.getSimpleName());
    public static final String IMG = makeCardPath("Anticipate.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    // /STAT DECLARATION/


    public Anticipate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        baseBlock = block = 6;
        checkMaskDesc();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new React());
        stanceChoices.add(new Read());

        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Condition());
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    void checkMaskDesc() {
        rawDescription = NohMask.shouldUseMaskDesc() ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            checkMaskDesc();
            initializeDescription();
        }
    }
}
