package com.root.sorcery.tileentity;

import com.root.sorcery.container.StaffCraftingInventory;
import com.root.sorcery.container.StaffLatheContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class StaffLatheTile extends ArcanaStorageTile implements INamedContainerProvider
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
            this.startArcanaVacuum(costIn);
            this.craftingCost = costIn;
            this.activeCraft = true;
        }
    }

    public void uncraft()
    {
        if (!world.isRemote())
        {
            this.inventory.uncraft();
        }
    }

    public double getProgressPercent()
    {
        return (double) this.arcanaUsed / (double) this.craftingCost;
    }

    @Override
    public void tick()
    {
        super.tick();
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
                    int cycleProgress = Math.min(this.craftSpeed, this.arcanaStorage.getArcanaStored());
                    this.arcanaUsed += cycleProgress;
                    this.arcanaStorage.extractArcana(cycleProgress, false);
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

    @Override
    public void read(CompoundNBT tag)
    {
        super.read(tag);
        if (tag.contains("inv"))
        {
            this.inventory.setFromTag(tag.getCompound("inv"));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt)
    {
        CompoundNBT tag = super.write(nbt);
        tag.put("inv", this.inventory.getAsTag());
        return tag;
    }
}
