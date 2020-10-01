package com.sorcery.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import static com.sorcery.item.ModItem.CRYSTAL_CARNELIAN;
import static com.sorcery.item.ModItem.CRYSTAL_CHALCEDONY;
import static com.sorcery.item.ModItem.GEODE;
import static com.sorcery.item.ModItem.CRYSTAL_JASPER;
import static com.sorcery.item.ModItem.CRYSTAL_NUUMMITE;
import static com.sorcery.item.ModItem.CRYSTAL_SERPENTINE;
import static com.sorcery.item.ModItem.CRYSTAL_SUGILITE;

public class GeodeItem extends Item
{
    public GeodeItem(Item.Properties properties)
    {
        super(properties);
    }
    
    


    public static boolean isGeode(Item item)
    {
        if (GEODE.get().getItem() == item)
            return true;
        return false;
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        Block struckBlock = context.getWorld().getBlockState(context.getPos()).getBlock();

        if (struckBlock == Blocks.STONECUTTER)
        {
            BlockPos pos = context.getPos();
            ItemStack crystal = new ItemStack(this::getRandomCrystal);
            ItemEntity crystalEntity = new ItemEntity(context.getWorld(), pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, crystal);
            context.getWorld().addEntity(crystalEntity);
            context.getItem().shrink(1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    private Item getRandomCrystal()
    {
        int randomNumb = (int) ((Math.random() * 6) + 1);

        switch (randomNumb)
        {
            case 1:
                return CRYSTAL_CARNELIAN.get();
            case 2:
                return CRYSTAL_CHALCEDONY.get();
            case 3:
                return CRYSTAL_SUGILITE.get();
            case 4:
                return CRYSTAL_JASPER.get();
            case 5:
                return CRYSTAL_SERPENTINE.get();
            case 6:
                return CRYSTAL_NUUMMITE.get();
            default:
                return null;
        }
    }
}
