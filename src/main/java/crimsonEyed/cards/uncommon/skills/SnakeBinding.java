package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;

import java.util.Iterator;

import static crimsonEyed.SasukeMod.makeCardPath;

public class SnakeBinding extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(SnakeBinding.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("SnakeBinding.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public SnakeBinding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        selfRetain = true;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (countCards() <= 0)
            return;
        if (Settings.FAST_MODE) {// 44
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));// 45
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));// 51
        }

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 58

        AbstractMonster mo;
        while (var3.hasNext()) {
            mo = (AbstractMonster) var3.next();
            this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber * countCards()), -this.magicNumber * countCards(), true, AbstractGameAction.AttackEffect.NONE));// 59
        }

        var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();// 69

        while (var3.hasNext()) {
            mo = (AbstractMonster) var3.next();
            if (!mo.hasPower("Artifact")) {// 70
                this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber * countCards()), this.magicNumber * countCards(), true, AbstractGameAction.AttackEffect.NONE));// 71
            }
        }
    }

    public static int countCards() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        return count;
    }

    public static boolean isStatusOrCurse(AbstractCard c) {
        return c.type == CardType.STATUS || c.type == CardType.CURSE;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();// 48
        magicNumber2 = countCards() * magicNumber;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 49
        this.initializeDescription();// 50
    }// 51
    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;// 80
        this.initializeDescription();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 55
        magicNumber2 = countCards() * magicNumber;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 56
        this.initializeDescription();// 57
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
