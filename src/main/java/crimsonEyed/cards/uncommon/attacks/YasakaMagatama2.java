package crimsonEyed.cards.uncommon.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.BurningPact;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.MagatamaAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.nio.BufferOverflowException;

import static crimsonEyed.SasukeMod.makeCardPath;

public class YasakaMagatama2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(YasakaMagatama2.class.getSimpleName());
    public static final String IMG = makeCardPath("YasakaMagatama.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 5;

    // /STAT DECLARATION/


    public YasakaMagatama2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Burn();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MagatamaAction(damage));
        }
        addToBot(new MakeTempCardInDiscardAction(new Burn(),1));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            initializeDescription();
        }
    }
}
