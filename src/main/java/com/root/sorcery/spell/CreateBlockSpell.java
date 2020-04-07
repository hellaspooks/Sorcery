package com.root.sorcery.spell;

import com.root.sorcery.block.ModBlock;
import com.root.sorcery.utils.Utils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CreateBlockSpell extends Spell
{
    public CreateBlockSpell()
    {
        super(100);
    }

    @Override
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        if (context.wasUsedOnBlock())
        {
            BlockPos pos = context.getFacePos();

            context.getWorld().setBlockState(pos, ModBlock.CONJURED_BLOCK.getDefaultState());
        } else {
            Vec3d posVec = Utils.nBlocksAlongVector(context.getPlayer().getEyePosition(1), context.getPlayer().getLookVec(), 2);
            BlockPos floatPos = new BlockPos(posVec);
            context.getWorld().setBlockState(floatPos, ModBlock.CONJURED_BLOCK.getDefaultState());
        }

        return ActionResultType.SUCCESS;
    }

}
