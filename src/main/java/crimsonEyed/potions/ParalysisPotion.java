package crimsonEyed.potions;

import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.StrengthPower;
import crimsonEyed.SasukeMod;

public class ParalysisPotion extends CustomPotion {

    public static final String POTION_ID = SasukeMod.makeID("ParalysisPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ParalysisPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.POISON);
        potency = getPotency();
        isThrown = true;
        targetRequired = true;
        labOutlineColor = Color.BLUE;
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -potency)));
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ParalysisPotion();
    }
}