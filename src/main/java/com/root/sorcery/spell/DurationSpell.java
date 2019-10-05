package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;

public class DurationSpell extends Spell
{
    int arcanaCost = 1;

    public DurationSpell()
    {
        // This is actually all that has to happen for a spell to have a cast duration,
        // everything else is handled in the DurationSpellEvent handler
        this.castDuration = 100;
    }

    // all this does is cast the spell multiple times
    @Override
    public ActionResultType cast(SpellUseContext context){

        if (!drainArcana(context, arcanaCost))
            return ActionResultType.FAIL;

        context.getPlayer().sendMessage(new StringTextComponent("Duration Spell Cast!"));

        return ActionResultType.SUCCESS;
    }

}
