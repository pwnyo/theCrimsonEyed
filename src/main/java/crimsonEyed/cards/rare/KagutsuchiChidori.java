package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class KagutsuchiChidori extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(KagutsuchiChidori.class.getSimpleName());
    public static final String IMG = makeCardPath("KagutsuchiChidori.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    private static final int DAMAGE = 10;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public KagutsuchiChidori() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    int countUniqueOrbs() {
        ArrayList<String> orbList = new ArrayList();// 20
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID)) {// 22
                orbList.add(o.ID);// 23
            }
        }
        return orbList.size();
    }

    public void applyPowers() {
        super.applyPowers();

        int realBaseDamage = this.baseDamage;// 85
        this.baseDamage += this.magicNumber * countUniqueOrbs();// 86
        super.applyPowers();// 88
        this.baseDamage = realBaseDamage;// 90
        this.isDamageModified = this.damage != this.baseDamage;// 93
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        int realBaseDamage = this.baseDamage;// 70
        this.baseDamage += this.magicNumber * countUniqueOrbs();// 71
        super.calculateCardDamage(mo);// 73
        this.baseDamage = realBaseDamage;// 75
        this.isDamageModified = this.damage != this.baseDamage;// 78
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}