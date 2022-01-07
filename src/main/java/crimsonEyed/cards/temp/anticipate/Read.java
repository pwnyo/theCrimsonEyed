package crimsonEyed.cards.temp.anticipate;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Read extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Read.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("Read.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int BLOCK = 3;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public Read() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }
    public Read(boolean generated) {
        this();
        applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        recalc();
        addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, block)));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, magicNumber)));
    }
    void recalc() {
        if (upgraded) {
            this.baseBlock = block = BLOCK + 2;
            this.upgradedBlock = true;
        }
        applyPowers();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            recalc();
            initializeDescription();
        }
    }
    @Override
    public AbstractCard makeCopy() {
        Read tmp = new Read();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            tmp.applyPowers();
        }

        return tmp;
    }
}
