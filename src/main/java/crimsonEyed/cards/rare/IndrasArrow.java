package crimsonEyed.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ForceField;
import com.megacrit.cardcrawl.cards.green.Eviscerate;
import com.megacrit.cardcrawl.cards.red.Cleave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.interfaces.IOnChannelListenerCard;
import crimsonEyed.patches.interfaces.IOnEvokeListenerCard;
import crimsonEyed.patches.interfaces.IOnLoseOrbSlotListenerCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class IndrasArrow extends AbstractDynamicCard implements IOnChannelListenerCard, IOnEvokeListenerCard, IOnLoseOrbSlotListenerCard {

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

    private static final int DAMAGE = 24;

    // /STAT DECLARATION/


    public IndrasArrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        isMultiDamage = true;
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
    public void triggerWhenDrawn() {
        countOrbs();
    }

    @Override
    public void onChannel(AbstractOrb o) {
        countOrbs();
    }

    @Override
    public void onEvoke(AbstractOrb o) {
        countOrbs();
    }

    @Override
    public void onLoseOrbSlot() {
        countOrbs();
    }

    void countOrbs() {
        countOrbs(0);
    }
    void countOrbs(int bonus) {
        if (CardCrawlGame.dungeon == null || AbstractDungeon.currMapNode == null ||
                AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        int count = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Lightning)
                count++;
        }
        count += bonus;
        BaseMod.logger.info("channeled orbs: " + count);
        setCostForTurn(cost - count);
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard tmp = new IndrasArrow();
        if (AbstractDungeon.player != null) {
            countOrbs();
        }
        return tmp;
    }
}