package com.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public class DurationSpell extends Spell
{

    public DurationSpell()
    {
        super(1);
        // This is actually all that has to happen for a spell to have a cast duration,
        // everything else is handled in the DurationSpellEvent handler
        this.castDuration = 100;
        this.castType = CastType.DURATION;
    }

    // This is triggered when the action finishes, not before
    @Override
    public ActionResultType doCastFinal(SpellUseContext context){

        context.getPlayer().sendMessage(new StringTextComponent("Duration Spell Cast!"));

        return ActionResultType.SUCCESS;
    }

}
