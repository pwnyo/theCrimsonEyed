package crimsonEyed.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.red.Disarm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.GenjutsuAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.Iterator;

import static crimsonEyed.SasukeMod.makeCardPath;
import static crimsonEyed.SasukeMod.makeID;

public class Genjutsu extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Genjutsu.class.getSimpleName());
    public static final String IMG = makeCardPath("Genjutsu.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int MAGIC2 = 1;

    // /STAT DECLARATION/


    public Genjutsu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = MAGIC2;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(makeID("GENJUTSU")));
       for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber2), -magicNumber2));
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
