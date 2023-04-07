package crimsonEyed.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.kaguchidori.DarkChoice;
import crimsonEyed.cards.temp.kaguchidori.LightningChoice;
import crimsonEyed.cards.temp.kaguchidori.PlasmaChoice;
import crimsonEyed.cards.uncommon.skills.Anticipate;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class KagutsuchiChidori extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(KagutsuchiChidori.class.getSimpleName());
    public static final String IMG = makeCardPath("KagutsuchiChidori.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 1;

    private static final int DAMAGE = 10;

    // /STAT DECLARATION/


    public KagutsuchiChidori() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        isEthereal = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new LightningChoice());
        choices.add(new DarkChoice());
        if (AbstractDungeon.player.hasRelic(NohMask.ID)) {
            choices.add(new PlasmaChoice());
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new ChooseOneAction(choices));
    }

    void checkMaskDesc() {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasRelic(NohMask.ID)) {
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
        else {
            rawDescription = cardStrings.DESCRIPTION;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
            checkMaskDesc();
            initializeDescription();
        }
    }
    @Override
    public AbstractCard makeCopy() {
        KagutsuchiChidori tmp = new KagutsuchiChidori();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasRelic(NohMask.ID)) {
            tmp.checkMaskDesc();
        }

        return tmp;
    }
}