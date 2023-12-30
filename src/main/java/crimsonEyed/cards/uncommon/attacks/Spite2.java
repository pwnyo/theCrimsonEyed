package crimsonEyed.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Spite2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Spite2.class.getSimpleName());
    public static final String IMG = makeCardPath("Spite.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Spite2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 4;
        tags.add(CardTags.HEALING);
        isEthereal = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VampireDamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
