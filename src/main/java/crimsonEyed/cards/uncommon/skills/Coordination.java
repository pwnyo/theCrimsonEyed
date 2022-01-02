package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.coordination.Eye;
import crimsonEyed.cards.temp.coordination.Feet;
import crimsonEyed.cards.temp.coordination.Hand;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Coordination extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Coordination.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("HandEyeCoordination.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int MAGIC2 = 1;

    // /STAT DECLARATION/


    public Coordination() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = MAGIC2;
        isEthereal = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Hand());
        stanceChoices.add(new Eye());
        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Feet());
        }
        if (upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }
        addToBot(new ChooseOneAction(stanceChoices));

    }
    public void applyPowers() {
        super.applyPowers();
        checkMaskDesc();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        checkMaskDesc();
    }
    void checkMaskDesc() {
        if (AbstractDungeon.player.hasRelic(NohMask.ID)) {
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        else {
            rawDescription = cardStrings.DESCRIPTION;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}
