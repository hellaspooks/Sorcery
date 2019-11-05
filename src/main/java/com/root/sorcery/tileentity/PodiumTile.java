package com.root.sorcery.tileentity;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PodiumTile extends TileEntity
{

    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAP = null;

    private ItemStackHandler inventory = new ItemStackHandler();

    public PodiumTile()
    {
        super(ModTile.PODIUM_TILE);
    }

    public IItemHandler getItemHandler()
    {
        return this.getCapability(ITEM_HANDLER_CAP, null).orElseThrow(NullPointerException::new);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ITEM_HANDLER_CAP)
        {
            return LazyOptional.of(() -> inventory).cast();
        }
        else
        {
            return LazyOptional.empty();
        }
    }

    @Override
    public void remove() {
        if (!this.getWorld().isRemote())
        {
            ItemStack heldItems = this.inventory.extractItem(0, 99, false);
            BlockPos pos = this.getPos();
            ItemEntity heldItemsEntity = new ItemEntity(this.getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, heldItems);
            this.getWorld().addEntity(heldItemsEntity);
        }
        this.removed = true;
        this.invalidateCaps();
        requestModelDataUpdate();
    }

}
