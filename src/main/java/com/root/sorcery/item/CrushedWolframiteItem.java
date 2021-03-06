package com.root.sorcery.item;

import com.root.sorcery.Constants;
import com.root.sorcery.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;

public class CrushedWolframiteItem extends Item
{
    public CrushedWolframiteItem()
    {
        super(Constants.ITEM_PROPS);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        if (!context.getWorld().isRemote())
        {
            Block block = context.getWorld().getBlockState(context.getPos()).getBlock();
            if (block.isIn(BlockTags.SAPLINGS))
            {
                context.getWorld().setBlockState(context.getPos(), ModBlock.RUNEWOOD_SAPLING.getDefaultState(), 3);
                context.getItem().shrink(1);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.FAIL;
        }
        return ActionResultType.SUCCESS;
    }
}
