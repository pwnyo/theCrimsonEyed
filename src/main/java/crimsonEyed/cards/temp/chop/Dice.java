package crimsonEyed.cards.temp.chop;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Dice extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Dice.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Dice.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    private AbstractMonster target;

    // /STAT DECLARATION/


    public Dice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 5;
        baseMagicNumber = magicNumber = 3;
        isMultiDamage = true;
    }
    public Dice(AbstractMonster m) {
        this();
        target = m;
        calculateCardDamage(m);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        damage = baseDamage;
        recalc();
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
    void recalc() {
        if (target == null)
            return;
        applyPowers();
        calculateCardDamage(target);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            recalc();
            initializeDescription();
        }
    }
}