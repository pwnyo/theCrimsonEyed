package crimsonEyed.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.patches.interfaces.IOnExhaustListenerCard;

import static crimsonEyed.SasukeMod.makeCardPath;
import static crimsonEyed.SasukeMod.makeID;

public class Kirin3 extends AbstractDynamicCard implements IOnExhaustListenerCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Kirin3.class.getSimpleName());
    public static final String IMG = makeCardPath("Kirin.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 10;

    // /STAT DECLARATION/


    public Kirin3() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        showEvokeValue = true;
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMPLETE && AbstractDungeon.player != null) {
            configureCostsOnNewCard();
        }
    }

    void configureCostsOnNewCard() {
        this.updateCost(-AbstractDungeon.player.exhaustPile.size());
    }

    @Override
    public void didExhaust() {
        SasukeMod.logger.info("kirin -1");
        this.updateCost(-1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(makeID("KIRIN")));
        AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), 0.8F));
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.CYAN.cpy()));
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(new Lightning()));
        }
        addToBot(new WaitAction(1f));
        addToBot(new EvokeAllOrbsAction());
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
