package crimsonEyed.cards.rare;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
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

    private static final int COST = 4;

    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DMG = 6;

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
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(3);
            initializeDescription();
        }
    }

    @Override
    public void triggerWhenDrawn() {
        countOrbs();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        countOrbs();
    }

    @Override
    public void onChannel(AbstractOrb o) {
        countOrbs();
    }

    @Override
    public void onEvoke(AbstractOrb o) {
        countOrbs(-1);
    }

    @Override
    public void onLoseOrbSlot() {
        countOrbs();
    }

    void countOrbs() {
        if (CardCrawlGame.dungeon == null || AbstractDungeon.currMapNode == null ||
                AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        int count = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (!(o instanceof EmptyOrbSlot))
                count++;
        }
        BaseMod.logger.info("channeled orbs: " + count);
        setCostForTurn(cost - count);
    }
    void countOrbs(int bonus) {
        if (CardCrawlGame.dungeon == null || AbstractDungeon.currMapNode == null ||
                AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        int count = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (!(o instanceof EmptyOrbSlot))
                count++;
        }
        count += bonus;
        BaseMod.logger.info("channeled orbs: " + count);
        setCostForTurn(cost - count);
    }

    /*
    @Override
    public AbstractCard makeCopy() {
        try {
            IndrasArrow arrow = getClass().newInstance();
            arrow.countOrbs();
            return arrow;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for card: " + this.cardID);
        }
    }*/
}