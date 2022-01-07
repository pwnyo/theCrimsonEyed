package crimsonEyed.cards.temp.chop;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Slash extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Slash.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Slash.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;
    private AbstractMonster target;

    // /STAT DECLARATION/


    public Slash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }
    public Slash(AbstractMonster m) {
        this();
        target = m;
        //applyPowers();
        calculateCardDamage(m);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }
    @Override
    public void onChoseThisOption() {
        if (target == null)
            return;
        recalc();
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    void recalc() {
        if (target == null)
            return;
        if (upgraded) {
            this.baseDamage = damage = DAMAGE + UPGRADE_PLUS_DMG;
            this.upgradedDamage = true;
        }
        SasukeMod.logger.info("upgraded? " + upgraded + " - damage is " + damage);
        applyPowers();
        calculateCardDamage(target);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            recalc();
            initializeDescription();
        }
    }
}