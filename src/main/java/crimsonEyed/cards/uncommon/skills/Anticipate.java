package crimsonEyed.cards.uncommon.skills;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.anticipate.Condition;
import crimsonEyed.cards.temp.anticipate.React;
import crimsonEyed.cards.temp.anticipate.Read;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Anticipate extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(Anticipate.class.getSimpleName());
    public static final String IMG = makeCardPath("Anticipate.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;
    private static final int BLOCK = 6;
    // /STAT DECLARATION/


    public Anticipate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = 1;
        baseMagicNumber2 = magicNumber2 = 3;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new React(true));

        if (p.hasRelic(NohMask.ID)) {
            stanceChoices.add(new Condition());
        }
        stanceChoices.add(new Read());
        if (upgraded) {
            for (AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    void checkMaskDesc() {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasRelic(NohMask.ID)) {
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[upgraded ? 1 : 0];
        }
        else {
            rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
            upgradeSecondMagicNumber(1);
            checkMaskDesc();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        Anticipate tmp = new Anticipate();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasRelic(NohMask.ID)) {
            tmp.checkMaskDesc();
        }

        return tmp;
    }
}
