package com.root.sorcery.container;

import com.root.sorcery.Constants;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class StaffCraftingInventory extends CraftingInventory
{
    private static ResourceLocation catalystTag = new ResourceLocation(Constants.MODID, "catalysts");
    private static ResourceLocation fittingTag = new ResourceLocation(Constants.MODID, "fittings");
    private static ResourceLocation rodTag = new ResourceLocation(Constants.MODID, "rods");

    public StaffCraftingInventory(Container eventHandlerIn, int width, int height)
    {
        super(eventHandlerIn, width, height);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        switch (index)
        {
            case 0:
                return ItemTags.getCollection().getOrCreate(fittingTag).contains(stack.getItem());
            case 1:
                return ItemTags.getCollection().getOrCreate(catalystTag).contains(stack.getItem());
            case 2:
                return ItemTags.getCollection().getOrCreate(rodTag).contains(stack.getItem());
            default:
                return false;
        }
    }
}
