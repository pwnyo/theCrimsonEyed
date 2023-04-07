package crimsonEyed.cards.uncommon.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.TriggerPassiveAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DarkImpulseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.BlazeBarrierAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class BlazeBarrier2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(BlazeBarrier2.class.getSimpleName());
    public static final String IMG = makeCardPath("BlazeBarrier.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 2;
    private static final int BLOCK = 10;

    // /STAT DECLARATION/


    public BlazeBarrier2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = 1;
        showEvokeValue = true;
        showEvokeOrbCount = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ChannelAction(new Dark()));
        addToBot(new TriggerPassiveAction(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Dark) {
                glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
