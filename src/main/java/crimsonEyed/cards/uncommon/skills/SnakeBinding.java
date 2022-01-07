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
    public static final String IMG = makeCardPath("SnakeBinding.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public SnakeBinding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseMagicNumber2 = magicNumber2 = 0;
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
        int badCount = countCards();

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber * badCount), -this.magicNumber * countCards(), true, AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber * badCount), this.magicNumber * countCards(), true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    int countCards() {
        int count = 0;
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.discardPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isStatusOrCurse(c)) {
                ++count;
            }
        }

        return count;
    }
    public void applyPowers() {
        super.applyPowers();
        int count = countCards();

        this.baseMagicNumber2 = magicNumber2 = count * magicNumber;

        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int count = countCards();

        this.baseMagicNumber2 = magicNumber2 = count * magicNumber2;

        rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public static boolean isStatusOrCurse(AbstractCard c) {
        return c.type == CardType.STATUS || c.type == CardType.CURSE;
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
