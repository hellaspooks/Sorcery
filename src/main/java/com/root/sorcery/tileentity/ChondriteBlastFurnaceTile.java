package com.root.sorcery.tileentity;

import com.root.sorcery.container.Containers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.tile.TileBase;

import javax.annotation.Nullable;

public class ChondriteBlastFurnaceTile extends TileBase implements INamedContainerProvider
{
    private final InventoryHandler inventory;

    private int burnTimeLeft;
    private int maxBurnTime;
    private int cookProgression;
    private int maxCookProgression;

    public ChondriteBlastFurnaceTile()
    {
        super(ModTile.CHRONDRITE_BLAST_FURNACE);

        inventory = new InventoryHandler(3);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        inventory.deserializeNBT(compound.getCompound("inventory"));

        burnTimeLeft = compound.getInt("burnTimeLeft");
        maxBurnTime = compound.getInt("maxBurnTime");
        cookProgression = compound.getInt("cookProgression");
        maxCookProgression = compound.getInt("maxCookProgression");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.put("inventory", inventory.serializeNBT());

        compound.putInt("burnTimeLeft", burnTimeLeft);
        compound.putInt("maxBurnTime", maxBurnTime);
        compound.putInt("cookProgression", cookProgression);
        compound.putInt("maxCookProgression", maxCookProgression);
        return super.write(compound);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("container.blast_furnace");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ContainerBuilder(Containers.CHONDRITE_BLAST_FURNACE, player)
                .player(player)
                .inventory()
                .hotbar()
                .tile(this, this.inventory)
                .slot(0, 0, 0)
                .fuelSlot(1, 0, 18)
                .outputSlot(2, 0, 36)
                .sync()
                .syncInteger(this::getBurnTimeLeft, this::setBurnTimeLeft)
                .syncInteger(this::getMaxBurnTime, this::setMaxBurnTime)
                .syncInteger(this::getCookProgression, this::setCookProgression)
                .syncInteger(this::getMaxCookProgression, this::setMaxCookProgression)
                .create(windowId);
    }

    public int getBurnTimeLeft()
    {
        return burnTimeLeft;
    }

    public void setBurnTimeLeft(int burnTimeLeft)
    {
        this.burnTimeLeft = burnTimeLeft;
    }

    public int getCookProgression()
    {
        return cookProgression;
    }

    public void setCookProgression(int cookProgression)
    {
        this.cookProgression = cookProgression;
    }

    public int getMaxBurnTime()
    {
        return maxBurnTime;
    }

    public void setMaxBurnTime(int maxBurnTime)
    {
        this.maxBurnTime = maxBurnTime;
    }

    public int getMaxCookProgression()
    {
        return maxCookProgression;
    }

    public void setMaxCookProgression(int maxCookProgression)
    {
        this.maxCookProgression = maxCookProgression;
    }

    public boolean isBurning()
    {
        return this.burnTimeLeft > 0;
    }
}
