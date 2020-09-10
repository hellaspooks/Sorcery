package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class RunewoodLogBlock extends LogBlock
{
    public RunewoodLogBlock()
    {
        super(MaterialColor.PURPLE, Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F, 2.0F).sound(SoundType.WOOD));
    }


    // To allow "stripping"
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result)
    {
        if (!world.isRemote())
        {
            ItemStack itemStack = player.getHeldItem(hand);
            if (itemStack.getItem() instanceof AxeItem)
            {
                BlockState sourceState = world.getBlockState(pos);
                world.setBlockState(pos, ModBlock.STRIPPED_RUNEWOOD_LOG.getRandomReplacementState(sourceState, world), 3);
                itemStack.damageItem(1, player, (p_220040_1_) -> p_220040_1_.sendBreakAnimation(hand));
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
}
