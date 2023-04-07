package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.green.Bane;
import com.megacrit.cardcrawl.cards.red.Dropkick;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.TsukuyomiAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;
import static crimsonEyed.SasukeMod.makeID;

public class Tsukuyomi extends AbstractDynamicCard {

    public static final String ID = SasukeMod.makeID(Tsukuyomi.class.getSimpleName());
    public static final String IMG = makeCardPath("Tsukuyomi.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int MAGIC = 8;
    private static final int UPGRADE_MAGIC = 3;

    public Tsukuyomi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = 0;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (mo == null || !isSelected) {
            magicNumber2 = 0;
            rawDescription = cardStrings.DESCRIPTION;
            return;
        }
        SasukeMod.logger.info("looking at monster " + mo.name);
        int count = 0;
        for (AbstractPower pow : mo.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF && !pow.ID.equals("Shackled")) {
                count++;
            }
        }
        magicNumber2 = count;
        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 2));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, !upgraded ? 2: 3, false)));
        addToBot(new TsukuyomiAction(m, magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
