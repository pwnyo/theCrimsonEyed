package crimsonEyed.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class YasakaMagatama2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(YasakaMagatama2.class.getSimpleName());
    public static final String IMG = makeCardPath("YasakaMagatama.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 9;

    // /STAT DECLARATION/


    public YasakaMagatama2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = 0;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < countDark(); i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.FIRE));
        }
    }
    int countDark() {
        int count = 0;
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Dark) {
                ++count;
            }
        }
        return count;
    }
    public void applyPowers() {
        super.applyPowers();
        int count = countDark();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        baseMagicNumber = magicNumber = count;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();

    }// 66

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;// 70
        this.initializeDescription();// 71
    }// 72

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int count = countDark();
        baseMagicNumber = magicNumber = count;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];

        this.initializeDescription();// 80
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}
