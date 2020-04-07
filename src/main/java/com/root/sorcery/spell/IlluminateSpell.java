package com.root.sorcery.spell;

import com.root.sorcery.block.ModBlock;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

public class IlluminateSpell extends Spell
{
    public IlluminateSpell()
    {
        super(100);
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.wasUsedOnBlock()) {
            BlockPos pos = context.getFacePos();

            context.getWorld().setBlockState(pos, ModBlock.CONJURED_LIGHT.getDefaultState());
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
