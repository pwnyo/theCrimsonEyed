package crimsonEyed.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.interfaces.IOnChannelListenerCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class IndrasArrow extends AbstractDynamicCard implements IOnChannelListenerCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(IndrasArrow.class.getSimpleName());
    public static final String IMG = makeCardPath("IndrasArrow.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 5;

    private static final int DAMAGE = 32;

    // /STAT DECLARATION/


    public IndrasArrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(8);
            initializeDescription();
        }
    }

    @Override
    public void onChannel(AbstractOrb o) {
        if (o instanceof Lightning) {
            updateCost(-1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        int count = 0;
        AbstractCard tmp = new IndrasArrow();
        if (AbstractDungeon.player != null) {
            for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
                if (o instanceof Lightning) {
                    count++;
                }
            }

            BaseMod.logger.info("channeled orbs: " + count);
        }
        tmp.updateCost(-count);
        return tmp;
    }
}