package crimsonEyed.cards.temp;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crimsonEyed.SasukeMod;
import crimsonEyed.cards.AbstractDynamicCard;

import static crimsonEyed.SasukeMod.makeCardPath;

public class EnduringFlame extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(EnduringFlame.class.getSimpleName());
    public static final String IMG = makeCardPath("EnduringFlame.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String BG_SM = "crimsonEyedResources/images/512/bg_skill_naruto.png";
    private static final String BG_LG = "crimsonEyedResources/images/1024/bg_skill_naruto.png";

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int BLOCK = 9;

    // /STAT DECLARATION/


    public EnduringFlame() {
        this(0);
    }
    public EnduringFlame(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        timesUpgraded = upgrades;

        setImg();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, 1));
        addToBot(new GainBlockAction(p, block));
    }

    public void upgrade() {
        this.upgradeBlock(3 + this.timesUpgraded);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

    private static void loadTextureFromString(String textureString) {
        if (!imgMap.containsKey(textureString)) {
            imgMap.put(textureString, ImageMaster.loadImage(textureString));
        }
    }

    private static Texture getTextureFromString(String textureString) {
        loadTextureFromString(textureString);
        return imgMap.get(textureString);
    }
    public Texture getBackgroundSmallTexture() {
        return getTextureFromString(BG_SM);
    }

    public Texture getBackgroundLargeTexture() {
        return getTextureFromString(BG_LG);
    }
    public void setImg() {
        setBackgroundTexture(BG_SM, BG_LG);
        setOrbTexture("crimsonEyedResources/images/512/card_naruto_energy.png",
                "crimsonEyedResources/images/1024/card_naruto_energy.png");
    }
    @Override
    public AbstractCard makeCopy() {
        EnduringFlame flame = new EnduringFlame(timesUpgraded);
        flame.setImg();
        return flame;
    }
}
