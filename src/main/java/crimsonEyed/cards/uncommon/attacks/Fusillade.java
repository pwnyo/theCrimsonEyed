package crimsonEyed.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Fusillade extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Fusillade.class.getSimpleName());
    public static final String IMG = makeCardPath("Fusillade.png");// "public static final String IMG = makeCardPath("Fusillade.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;  // COST = 1

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public Fusillade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
    }
    int countCards() {
        int count = -1;

        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals(Fusillade.ID)) {
                count++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.cardID.equals(Fusillade.ID)) {
                count++;
            }
        }
        for (AbstractCard c : p.drawPile.group) {
            if (c.cardID.equals(Fusillade.ID)) {
                count++;
            }
        }
        return count;
    }

    public void applyPowers() {
        super.applyPowers();

        this.baseMagicNumber = MAGIC + countCards();
        magicNumber = baseMagicNumber;

        if (countCards() > 0)
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        this.baseMagicNumber = MAGIC + countCards();
        magicNumber = baseMagicNumber;

        if (countCards() > 0)
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}