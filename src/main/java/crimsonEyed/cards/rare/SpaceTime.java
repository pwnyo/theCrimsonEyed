package crimsonEyed.cards.rare;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Evolve;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
import crimsonEyed.cards.temp.spacetime.AlmightyPush;
import crimsonEyed.cards.temp.spacetime.Amenotejikara;
import crimsonEyed.cards.temp.spacetime.UniversalPull;
import crimsonEyed.characters.TheCrimsonEyed;
import crimsonEyed.relics.rarer.NohMask;

import java.util.ArrayList;

import static crimsonEyed.SasukeMod.makeCardPath;

public class SpaceTime extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(SpaceTime.class.getSimpleName());
    public static final String IMG = makeCardPath("SpaceTime.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 0;
    private AbstractCard cardsToPreview2;

    // /STAT DECLARATION/


    public SpaceTime() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new UniversalPull();
        cardsToPreview2 = new Amenotejikara();
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        if (upgraded) {
            cardsToPreview.upgrade();
            cardsToPreview2.upgrade();
        }
        super.renderCardPreviewInSingleView(sb);
        this.cardsToPreview2.current_x = 490.0F * Settings.scale;
        this.cardsToPreview2.current_y = 290.0F * Settings.scale;
        this.cardsToPreview2.drawScale = 0.8F;
        this.cardsToPreview2.render(sb);
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (AbstractDungeon.player == null ||
                (AbstractDungeon.player != null && !AbstractDungeon.player.isDraggingCard)) {
            if (upgraded) {
                cardsToPreview.upgrade();
                cardsToPreview2.upgrade();
            }
            float tmpScale = this.drawScale * 0.5F;

            if (this.current_x > (float)Settings.WIDTH * 0.75F) {
                this.cardsToPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;
                this.cardsToPreview2.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;
            } else {
                this.cardsToPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;
                this.cardsToPreview2.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;
            }

            this.cardsToPreview.current_y = this.current_y + (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.5F) * this.drawScale;
            this.cardsToPreview.drawScale = tmpScale;
            this.cardsToPreview.render(sb);
            this.cardsToPreview2.current_y = this.current_y - (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.5F) * this.drawScale;
            this.cardsToPreview2.drawScale = tmpScale;
            this.cardsToPreview2.render(sb);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();

        choices.add(new UniversalPull());
        choices.add(new Amenotejikara());

        if (AbstractDungeon.player.hasRelic(NohMask.ID)) {
            choices.add(new AlmightyPush());
        }
        if (upgraded) {
            for (AbstractCard c : choices) {
                c.upgrade();
            }
        }

        addToBot(new ChooseOneAction(choices));
    }
    void checkMaskDesc() {
        if (NohMask.shouldUseMaskDesc()) {
            rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
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
            checkMaskDesc();

            UniversalPull up = new UniversalPull();
            Amenotejikara am = new Amenotejikara();
            up.upgrade();
            am.upgrade();
            cardsToPreview = up;
            cardsToPreview2 = am;

            initializeDescription();
        }
    }
    @Override
    public AbstractCard makeCopy() {
        SpaceTime tmp = new SpaceTime();
        tmp.checkMaskDesc();
        return tmp;
    }
}
