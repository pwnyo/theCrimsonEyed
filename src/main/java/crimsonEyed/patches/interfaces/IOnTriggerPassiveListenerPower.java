package crimsonEyed.patches.interfaces;

import com.megacrit.cardcrawl.orbs.AbstractOrb;

public interface IOnTriggerPassiveListenerPower {
    //For powers only
    void onTriggerPassive(AbstractOrb o, boolean atStart);
}
