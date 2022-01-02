package crimsonEyed.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import crimsonEyed.SasukeMod;
import crimsonEyed.actions.unique.SixPathsAction;

public class YinVessel extends CustomPotion {

    public static final String POTION_ID = SasukeMod.makeID("YinVessel");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public YinVessel() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.MOON, PotionColor.SMOKE);
        potency = getPotency();
        isThrown = false;
        labOutlineColor = Color.BLUE;
    }

    @Override
    public void initializeData() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {// 27
            this.description = potionStrings.DESCRIPTIONS[1];// 30
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];// 28
        }

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new SixPathsAction(potency));
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new YinVessel();
    }
}
