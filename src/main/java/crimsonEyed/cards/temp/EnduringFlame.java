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
import crimsonEyed.characters.TheCrimsonEyed;

import static crimsonEyed.SasukeMod.makeCardPath;

public class EnduringFlame extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SasukeMod.makeID(EnduringFlame.class.getSimpleName());
    public static final String IMG = makeCardPath("EnduringFlame.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheCrimsonEyed.Enums.SASUKE_BLUE;

    private static final int COST = 0;
    private static final int BLOCK = 9;

    // /STAT DECLARATION/


    public EnduringFlame() {
        this(0);
    }
    public EnduringFlame(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        timesUpgraded = upgrades;

        setBackgroundTexture("crimsonEyedResources/images/512/bg_skill_naruto.png",
                "crimsonEyedResources/images/1024/bg_skill_naruto.png");
        setOrbTexture("crimsonEyedResources/images/512/card_naruto_energy.png",
                "crimsonEyedResources/images/1024/card_naruto_energy.png");
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
        return (Texture)imgMap.get(textureString);
    }
    public Texture getBackgroundSmallTexture() {
        return getTextureFromString("crimsonEyedResources/images/512/bg_skill_naruto.png");
    }

    public Texture getBackgroundLargeTexture() {
        return getTextureFromString("crimsonEyedResources/images/1024/bg_skill_naruto.png");
    }
    @Override
    public AbstractCard makeCopy() {
        EnduringFlame flame = new EnduringFlame(timesUpgraded);
        setBackgroundTexture("crimsonEyedResources/images/512/bg_skill_naruto.png",
                "crimsonEyedResources/images/1024/bg_skill_naruto.png");
        setOrbTexture("crimsonEyedResources/images/512/card_naruto_energy.png",
                "crimsonEyedResources/images/1024/card_naruto_energy.png");
        return flame;
    }
}
