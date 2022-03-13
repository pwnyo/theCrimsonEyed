package crimsonEyed.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class Hatred extends AbstractDynamicCard {
    public static final String ID = SasukeMod.makeID(Hatred.class.getSimpleName());
    public static final String IMG = makeCardPath("Hatred.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.CURSE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.CURSE;       //
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 1;
    private static final int MAGIC = 1;

    public Hatred() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        SoulboundField.soulbound.set(this, true);
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.dontTriggerOnUseCard) {
            exhaust = false;
            this.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        }
        else {
            exhaust = true;
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        SasukeMod.logger.info("hatred is hurting");
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        //this.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
    }
    public void upgrade() {
        upgradeMagicNumber(1);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;// 52
        this.initializeTitle();
    }
}
