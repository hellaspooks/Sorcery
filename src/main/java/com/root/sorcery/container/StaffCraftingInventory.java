package com.root.sorcery.container;

import com.root.sorcery.Constants;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;

public class StaffCraftingInventory extends CraftingInventory
{

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
                return ItemTags.getCollection().getOrCreate(Constants.FITTING_TAG).contains(stack.getItem());
            case 1:
                return ItemTags.getCollection().getOrCreate(Constants.CATALYST_TAG).contains(stack.getItem());
            case 2:
                return ItemTags.getCollection().getOrCreate(Constants.ROD_TAG).contains(stack.getItem());
            default:
                return false;
        }
    }
}
