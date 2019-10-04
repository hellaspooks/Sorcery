package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TestSpell extends Spell
{

    private String debugMessage;

    public TestSpell(String debugMessageIn)
    {
        this.debugMessage = debugMessageIn;

    }

    @Override
    public ActionResultType cast(SpellUseContext context)
    {
        if (!drainArcana(context, 0))
            return ActionResultType.FAIL;

        World world = context.getWorld();
        if ( context.getPlayer() != null){

            if (!world.isRemote())
            {
                context.getPlayer().sendMessage(new StringTextComponent(this.debugMessage));
            }
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.FAIL;
        }

    }
}
