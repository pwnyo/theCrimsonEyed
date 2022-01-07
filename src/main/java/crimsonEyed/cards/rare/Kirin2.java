package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.powers.LoseFocusPower;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Kirin2 extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Kirin2.class.getSimpleName());
    public static final String IMG = makeCardPath("Kirin.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public Kirin2() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        showEvokeValue = true;
        showEvokeOrbCount = 3;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int focus = getFocus();
        if (focus > 0) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, focus)));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(p, focus)));
        }
        addToBot(new EvokeAllOrbsAction());
        if (!upgraded)
            addToBot(new PressEndTurnButtonAction());
    }
    int getFocus() {
        return AbstractDungeon.player.exhaustPile.size();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
