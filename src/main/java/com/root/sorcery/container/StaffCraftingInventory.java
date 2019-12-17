package com.root.sorcery.container;

import com.root.sorcery.Constants;
import com.root.sorcery.item.ModItem;
import com.root.sorcery.item.StaffComponentItem;
import com.root.sorcery.item.StaffItem;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.StaffCraftPacket;
import com.root.sorcery.tileentity.StaffLatheTile;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;

public class StaffCraftingInventory extends Inventory
{

    private StaffLatheTile tile;
    public boolean craftReady = false;

    private ItemStack pendingCraft = ItemStack.EMPTY;
    private int craftCost = 0;


    public StaffCraftingInventory(StaffLatheTile tile)
    {
        super(5);
        this.tile = tile;
        this.addListener(new StaffCraftingListener(this));
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
            case 3:
                return stack.getItem() instanceof StaffItem;
            default:
                return false;
        }
    }

    public void finishCraft()
    {
        if (!(this.tile.getWorld().isRemote))
        {
            System.out.println("On server Side");
            System.out.println(pendingCraft);

            ItemStack staffOut = pendingCraft.copy();


            this.decrStackSize(0, 1);
            this.decrStackSize(1, 1);
            this.decrStackSize(2, 1);

            this.setInventorySlotContents(3, staffOut);


            this.pendingCraft = ItemStack.EMPTY;

        } else {
            System.out.println("on client Side");
            System.out.println(pendingCraft);
        }

        this.craftCost = 0;
        this.craftReady = false;
        this.markDirty();
    }

    public void clearPendingCraft()
    {
        this.pendingCraft = ItemStack.EMPTY;
        this.craftReady = false;
        this.craftCost = 0;
    }

    public void setPendingCraft(ItemStack pendingCraft, int arcanaCost)
    {
        this.pendingCraft = pendingCraft;
        this.craftCost = arcanaCost;
        this.craftReady = true;
    }

    public void startPendingCraft()
    {
        StaffCraftPacket pkt = new StaffCraftPacket(this.tile.getPos(), this.craftCost);
        PacketHandler.sendToServer(pkt);
    }

    public void uncraft()
    {
        ItemStack staffItem = getStackInSlot(3);
        if (staffItem.getItem() instanceof StaffItem)
        {
           CompoundNBT tag = staffItem.getTag();
        }
    }


    public boolean areComponentsValid(ItemStack fitting, ItemStack catalyst, ItemStack rod)
    {
        if (ItemTags.getCollection().getOrCreate(Constants.FITTING_TAG).contains(fitting.getItem())) {
            if (ItemTags.getCollection().getOrCreate(Constants.CATALYST_TAG).contains(catalyst.getItem())) {
                if (ItemTags.getCollection().getOrCreate(Constants.ROD_TAG).contains(rod.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    class StaffCraftingListener implements IInventoryChangedListener
    {
        private StaffCraftingInventory inventory;

        public StaffCraftingListener(StaffCraftingInventory inventory)
        {
            this.inventory = inventory;
        }

        @Override
        public void onInventoryChanged(IInventory invBasic)
        {
            ItemStack fitting = invBasic.getStackInSlot(0);
            ItemStack catalyst = invBasic.getStackInSlot(1);
            ItemStack rod = invBasic.getStackInSlot(2);

            if (areComponentsValid(fitting, catalyst, rod)) {
                int arcanaCost = 0;
                StaffComponentItem c1 = (StaffComponentItem) fitting.getItem();
                StaffComponentItem c2 = (StaffComponentItem) catalyst.getItem();
                StaffComponentItem c3 = (StaffComponentItem) rod.getItem();
                ItemStack craftedStaff = new ItemStack(ModItem.sorcerous_staff);
                CompoundNBT nbt = craftedStaff.getOrCreateTag();
                nbt.putString("staffType", c2.modelString);
                nbt.putString("rod", c3.modelString);
                nbt.putString("catalyst", c2.modelString);
                nbt.putString("fitting", c1.modelString);

                arcanaCost += c1.arcanaCost;
                arcanaCost += c2.arcanaCost;
                arcanaCost += c3.arcanaCost;

                craftedStaff.setTag(nbt);

                this.inventory.setPendingCraft(craftedStaff, arcanaCost);
            } else {
                this.inventory.clearPendingCraft();
            }
        }
    }

}
