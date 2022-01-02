package crimsonEyed.cards.rare;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;
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
        exhaust = true;
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        super.renderCardPreviewInSingleView(sb);
        this.cardsToPreview2.current_x = 490.0F * Settings.scale;// 2983
        this.cardsToPreview2.current_y = 290.0F * Settings.scale;// 2984
        this.cardsToPreview2.drawScale = 0.8F;// 2985
        this.cardsToPreview2.render(sb);// 2986
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {// 2990
            float tmpScale = this.drawScale * 0.5F;// 2994

            if (this.current_x > (float)Settings.WIDTH * 0.75F) {// 2996
                this.cardsToPreview.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;// 2997
                this.cardsToPreview2.current_x = this.current_x + (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;// 2997
            } else {
                this.cardsToPreview.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;// 3000
                this.cardsToPreview2.current_x = this.current_x - (IMG_WIDTH / 2.0F + IMG_WIDTH / 2.0F * 0.5F + 16.0F) * this.drawScale;// 3000
            }

            this.cardsToPreview.current_y = this.current_y + (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.5F) * this.drawScale;// 3004
            this.cardsToPreview.drawScale = tmpScale;// 3007
            this.cardsToPreview.render(sb);// 3008
            this.cardsToPreview2.current_y = this.current_y - (IMG_HEIGHT / 2.0F - IMG_HEIGHT / 2.0F * 0.5F) * this.drawScale;// 3004
            this.cardsToPreview2.drawScale = tmpScale;// 3007
            this.cardsToPreview2.render(sb);// 3008
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList();// 42

        choices.add(new UniversalPull());// 43
        choices.add(new Amenotejikara());// 44

        if (upgraded) {
            for (AbstractCard c : choices) {
                c.upgrade();
            }
        }

        addToBot(new ChooseOneAction(choices));
    }
    void checkMaskDesc() {
        if (AbstractDungeon.player.hasRelic(NohMask.ID)) {
            rawDescription = upgraded ? cardStrings.EXTENDED_DESCRIPTION[0] : cardStrings.EXTENDED_DESCRIPTION[1];
        }
        else {
            rawDescription = cardStrings.DESCRIPTION;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;

            UniversalPull up = new UniversalPull();
            Amenotejikara am = new Amenotejikara();
            up.upgrade();
            am.upgrade();
            cardsToPreview = up;
            cardsToPreview2 = am;

            initializeDescription();
        }
    }
}
