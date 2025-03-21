package crimsonEyed.cards.temp.kaguchidori;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class LightningChoice extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(LightningChoice.class.getSimpleName());
    public static final String IMG = makeCardPath("Lightning.png");

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    // /STAT DECLARATION/

    public LightningChoice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new ChannelAction(new Lightning()));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
    }
}
