package com.root.sorcery.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;

import static com.root.sorcery.item.ModItem.carnelian;
import static com.root.sorcery.item.ModItem.chalcedony;
import static com.root.sorcery.item.ModItem.geode;
import static com.root.sorcery.item.ModItem.jasper;
import static com.root.sorcery.item.ModItem.nuummite;
import static com.root.sorcery.item.ModItem.serpentine;
import static com.root.sorcery.item.ModItem.sugilite;

public class GeodeItem extends Item
{
    public GeodeItem(Item.Properties properties)
    {
        super(properties);
    }
    
    


    public static boolean isGeode(Item item)
    {
        if (geode.getItem() == item)
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
                return carnelian;
            case 2:
                return chalcedony;
            case 3:
                return sugilite;
            case 4:
                return jasper;
            case 5:
                return serpentine;
            case 6:
                return nuummite;
            default:
                return null;
        }
    }
}
