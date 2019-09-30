package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class TestSpell extends Spell
{

    public TestSpell()
    {

    }

    @Override
    public ActionResultType cast(SpellUseContext context)
    {
        World world = context.getWorld();
        if ( context.getPlayer() != null){

            if (!world.isRemote())
            {
                context.getPlayer().sendMessage(new StringTextComponent("Test Spell Cast!"));
            }
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.FAIL;
        }

    }
}
