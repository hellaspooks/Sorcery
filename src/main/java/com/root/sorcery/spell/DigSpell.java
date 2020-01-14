package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class DigSpell extends Spell
{
    public DigSpell(int arcanaCost)
    {
        super(arcanaCost);
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {
        BlockPos pos = context.getHitPos();
        if (pos == null)
        {
            return ActionResultType.FAIL;
        }

        float hardness = context.getWorld().getBlockState(pos).getBlockHardness(context.getWorld(), pos);


        return ActionResultType.SUCCESS;
    }
}
