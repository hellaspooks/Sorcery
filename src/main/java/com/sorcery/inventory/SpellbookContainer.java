package com.sorcery.inventory;


import com.sorcery.item.SpellbookItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SpellbookContainer extends Container
{
    private final IInventory spellbookInventory;
    private final int numRows;
    private final PlayerInventory playerInventory;

    private SpellbookContainer(ContainerType<?> type, int id, PlayerInventory player, int rows) {
        this(type, id, player, new Inventory(9 * rows), rows);
    }

    public SpellbookContainer(int id, PlayerInventory playerInventory, SpellbookInventory spellbookInventory)
    {
        this(ContainerType.GENERIC_9X1, id, playerInventory, spellbookInventory, 1);
    }


    public static SpellbookContainer createGeneric9X1(int id, PlayerInventory player) {
        return new SpellbookContainer(ContainerType.GENERIC_9X1, id, player, 1);
    }

    public SpellbookContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IInventory spellbookInventory, int rows) {
        super(type, id);
        this.playerInventory = playerInventoryIn;
        assertInventorySize(spellbookInventory, rows * 9);
        this.spellbookInventory = spellbookInventory;
        this.numRows = rows;
        spellbookInventory.openInventory(playerInventoryIn.player);
        int i = (this.numRows - 4) * 18;

        for(int j = 0; j < this.numRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(spellbookInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventoryIn, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventoryIn, i1, 8 + i1 * 18, 161 + i));
        }

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        if (spellbookInventory instanceof SpellbookInventory)
        {
            final ItemStack stack = ((SpellbookInventory) spellbookInventory).getStack();
            return !stack.isEmpty() && stack.getItem() instanceof SpellbookItem;
        }
        return true;
    }

    public Integer getNumRows()
    {
        return this.numRows;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ( spellbookInventory instanceof SpellbookInventory) {
            ((SpellbookInventory) spellbookInventory).writeItemStack(this.playerInventory);
        }
    }

}
