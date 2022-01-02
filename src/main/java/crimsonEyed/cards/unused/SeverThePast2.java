package crimsonEyed.cards.unused;

import basemod.AutoAdd;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.powers.SeverThePastPower;

import static crimsonEyed.SasukeMod.makeCardPath;

@AutoAdd.Ignore
public class SeverThePast2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(SeverThePast2.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;

    public static final String IMG = makeCardPath("Skill.png");// "public static final String IMG = makeCardPath("SeverThePast2.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 3;  // COST = 3
    private static final int UPGRADED_COST = 2; // UPGRADED_COST = 2

    // /STAT DECLARATION/


    public SeverThePast2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33F));// 39
        this.addToBot(new SFXAction("ATTACK_FIRE"));// 40
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.33F));// 41
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CYAN, p.hb.cX, p.hb.cY), 0.0F));// 42
        this.addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));// 43
        boolean powerExists = false;// 45
        for (AbstractPower pow : p.powers) {
            if (pow.ID.equals(SeverThePastPower.POWER_ID)) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(p, p, new SeverThePastPower(p)));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
