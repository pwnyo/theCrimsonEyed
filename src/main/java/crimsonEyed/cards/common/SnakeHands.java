package crimsonEyed.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.ThunderStrike;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import crimsonEyed.DefaultMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheDefault;

import java.util.Iterator;

import static crimsonEyed.DefaultMod.makeCardPath;

public class SnakeHands extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(SnakeHands.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 3;


    public SnakeHands() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.POISON));
    }

    public static int countCards() {
        int count = 0;
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.discardPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        return count;
    }

    public static boolean isStatusOrCurse(AbstractCard c) {
        return c.type == CardType.STATUS || c.type == CardType.CURSE;
    }

    public void applyPowers() {
        super.applyPowers();

        int realBaseDamage = this.baseDamage;// 85
        this.baseDamage += this.magicNumber * countCards();// 86
        super.applyPowers();// 88
        this.baseDamage = realBaseDamage;// 90
        this.isDamageModified = this.damage != this.baseDamage;// 93
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        int realBaseDamage = this.baseDamage;// 70
        this.baseDamage += this.magicNumber * countCards();// 71
        super.calculateCardDamage(mo);// 73
        this.baseDamage = realBaseDamage;// 75
        this.isDamageModified = this.damage != this.baseDamage;// 78
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}