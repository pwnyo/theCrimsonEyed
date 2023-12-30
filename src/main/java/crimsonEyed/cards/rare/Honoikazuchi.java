package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Dualcast;
import com.megacrit.cardcrawl.cards.blue.Recursion;
import com.megacrit.cardcrawl.cards.colorless.MindBlast;
import com.megacrit.cardcrawl.cards.red.BodySlam;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.HonoikazuchiAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.TempElectroPatch;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Honoikazuchi extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Honoikazuchi.class.getSimpleName());
    public static final String IMG = makeCardPath("Honoikazuchi.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 0;

    // /STAT DECLARATION/


    public Honoikazuchi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        isMultiDamage = true;
        showEvokeValue = true;
        showEvokeOrbCount = 1;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HonoikazuchiAction());
    }
    public void applyPowers() {
        if (!AbstractDungeon.player.orbs.isEmpty() && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot)) {
            baseDamage = AbstractDungeon.player.orbs.get(0).evokeAmount;
        }
        super.applyPowers();
        redesc();
    }

    public void onMoveToDiscard() {
        baseDamage = damage = 0;
        this.rawDescription = !upgraded ? cardStrings.DESCRIPTION : cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        redesc();
    }
    void redesc() {
        this.rawDescription = (!upgraded ? cardStrings.DESCRIPTION : cardStrings.UPGRADE_DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }
}