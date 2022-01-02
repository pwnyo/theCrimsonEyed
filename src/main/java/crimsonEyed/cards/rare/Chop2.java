package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.chop.Dice;
import crimsonEyed.cards.temp.chop.Rend;
import crimsonEyed.cards.temp.chop.Slash;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Chop2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Chop2.class.getSimpleName());
    public static final String IMG = makeCardPath("Chop.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;

    // /STAT DECLARATION/


    public Chop2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Slash(m));
        stanceChoices.add(new Rend(m));
        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Dice(m));
        }
        if (this.upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }
    public void applyPowers() {
        int normalDmg = baseDamage;

        this.baseDamage = baseMagicNumber;
        super.applyPowers();
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;

        this.baseDamage = normalDmg;
        super.applyPowers();
        checkMaskDesc();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int normalDmg = baseDamage;
        this.baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo);
        this.magicNumber = this.damage;
        this.isMagicNumberModified = this.isDamageModified;

        this.baseDamage = normalDmg;
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
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}