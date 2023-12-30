package crimsonEyed.cards.uncommon.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.common.GainEvokeBlock;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class BlazeBarrier2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(BlazeBarrier2.class.getSimpleName());
    public static final String IMG = makeCardPath("BlazeBarrier.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;

    // /STAT DECLARATION/


    public BlazeBarrier2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 1;
        showEvokeValue = true;
        showEvokeOrbCount = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Dark()));
        if (upgraded) {
            addToBot(new TriggerPassiveAction(magicNumber));
        }
        addToBot(new GainEvokeBlock());
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
