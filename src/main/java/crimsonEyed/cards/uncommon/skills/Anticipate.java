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
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("Anticipate.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int MAGIC = 6;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    // /STAT DECLARATION/


    public Anticipate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = 1;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new React());
        stanceChoices.add(new Read());

        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Condition());
        }

        if (this.upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }
    public void applyPowers() {
        this.baseBlock += 3;
        this.baseMagicNumber = this.baseBlock;
        super.applyPowers();
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.isBlockModified;
        this.baseBlock -= 3;
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
            rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[0] : cardStrings.EXTENDED_DESCRIPTION[1];
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
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}