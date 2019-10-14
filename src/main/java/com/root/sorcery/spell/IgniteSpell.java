package com.root.sorcery.spell;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class IgniteSpell extends Spell
{
    public IgniteSpell()
    {
        this.sound = SoundEvents.ITEM_FIRECHARGE_USE;
    }

    @Override
    public ActionResultType castServer(SpellUseContext context)
    {
        if (context.getHitPos() != null)
        {
            BlockPos firePos = context.getHitPos().offset(context.getHitFace());
            BlockState blockState = ((FireBlock) Blocks.FIRE).getStateForPlacement(context.getWorld(), firePos);
            context.getWorld().setBlockState(firePos, blockState, 11);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

}
