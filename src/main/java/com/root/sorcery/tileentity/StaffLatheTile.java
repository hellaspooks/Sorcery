package com.root.sorcery.tileentity;

import com.root.sorcery.container.StaffCraftingInventory;
import com.root.sorcery.container.StaffLatheContainer;
import com.root.sorcery.network.PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class StaffLatheTile extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{

    private StaffCraftingInventory inventory;

    public int craftingCost = 0;
    public int arcanaUsed = 0;

    private int craftSpeed = 1;

    private boolean activeCraft = false;

    public StaffLatheTile()
    {
        super(ModTile.STAFF_LATHE_TILE);
        this.inventory = new StaffCraftingInventory(this);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new StaffLatheContainer(i, world, pos, playerInventory, playerEntity);
    }

    public StaffCraftingInventory getInventory()
    {
        return this.inventory;
    }

    public void startCraft(int costIn)
    {
        if (!world.isRemote())
        {
            this.craftingCost = costIn;
            this.activeCraft = true;
        }
    }

    public double getProgressPercent()
    {
        return (double) this.arcanaUsed / (double) this.craftingCost;
    }

    @Override
    public void tick()
    {
        if (!world.isRemote())
        {
            if (this.activeCraft)
            {
                if (this.arcanaUsed >= this.craftingCost)
                {
                    this.craftingCost = 0;
                    this.arcanaUsed = 0;
                    this.activeCraft = false;
                    this.inventory.finishCraft();
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                } else {
                    this.arcanaUsed += this.craftSpeed;
                    world.notifyBlockUpdate(pos, this.getBlockState(), this.getBlockState(), 3);
                }
            }
        }
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        this.craftingCost = tag.getInt("aM");
        this.arcanaUsed = tag.getInt("aU");
    }

    // get tag to send client
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = this.write(new CompoundNBT());
        nbt.putInt("aM", this.craftingCost);
        nbt.putInt("aU", this.arcanaUsed);
        return nbt;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

}
