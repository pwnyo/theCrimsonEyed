package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.FTL;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.IntensifyAction;
import crimsonEyed.actions.unique.SnakeBindingAction;
import crimsonEyed.actions.unique.SnakeHandsAction;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.ScryListenPatch;

import java.util.Iterator;

import static crimsonEyed.SasukeMod.makeCardPath;

public class SnakeBinding extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(SnakeBinding.class.getSimpleName());
    public static final String IMG = makeCardPath("SnakeBinding.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int MAGIC = 4;

    // /STAT DECLARATION/


    public SnakeBinding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SnakeBindingAction(this, magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        if (SnakeHandsAction.hasStatusOrCurse()) {
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
        else {
            glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
