package crimsonEyed.cards.uncommon.skills;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Analyze extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Analyze.class.getSimpleName());
    public static final String IMG = makeCardPath("Analyze.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int MAGIC = 3;

    // /STAT DECLARATION/


    public Analyze() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();
        this.isBlockModified = false;
        float tmp = (float)this.baseBlock;

        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (focus != null) {
            tmp += focus.amount * magicNumber;
        }

        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        for (AbstractPower p : AbstractDungeon.player.powers) {
            tmp = p.modifyBlock(tmp, this);
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            tmp = p.modifyBlockLast(tmp);
        }

        this.block = MathUtils.floor(tmp);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            initializeDescription();
        }
    }
}
