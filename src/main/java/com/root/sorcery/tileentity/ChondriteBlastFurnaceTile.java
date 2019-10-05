package com.root.sorcery.tileentity;

import com.root.sorcery.container.Containers;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.voxelindustry.steamlayer.container.ContainerBuilder;
import net.voxelindustry.steamlayer.inventory.InventoryHandler;
import net.voxelindustry.steamlayer.tile.TileBase;
import net.voxelindustry.steamlayer.utils.ItemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ChondriteBlastFurnaceTile extends TileBase implements INamedContainerProvider, ITickableTileEntity
{
    private final InventoryHandler inventory;
    private final RecipeWrapper    recipeInventory;

    private final int burnSpeed = 1;
    private final int cookSpeed = 1;

    private int burnTimeLeft;
    private int maxBurnTime;
    private int cookProgression;
    private int maxCookProgression;

    private Optional<BlastingRecipe> cachedRecipe = Optional.empty();
    private boolean                  isInventoryDirty;

    public ChondriteBlastFurnaceTile()
    {
        super(ModTile.CHONDRITE_BLAST_FURNACE_TILE);

        inventory = new InventoryHandler(3);
        inventory.setOnSlotChange(slot ->
        {
            markDirty();
            if (slot == 0) isInventoryDirty = true;
        });
        recipeInventory = new RecipeWrapper(inventory);
    }

    @Override
    public void tick()
    {
        if (isClient())
            return;

        boolean lastBurnState = isBurning();

        if (isBurning())
        {
            burnTimeLeft -= burnSpeed;
            if (burnTimeLeft < 0)
                burnTimeLeft = 0;
        }

        if (isInventoryDirty)
        {
            refreshCachedRecipe();

            maxCookProgression = 0;
            cookProgression = 0;
            cachedRecipe.ifPresent(blastingRecipe -> maxCookProgression = blastingRecipe.getCookTime());
        }

        if (canSmelt())
        {
            if (isBurning())
            {
                cookProgression += cookSpeed;

                if (cookProgression >= maxCookProgression)
                {
                    cookProgression = 0;
                    maxCookProgression = 0;
                    inventory.extractItem(0, 1, false);
                    inventory.insertItem(2, cachedRecipe.get().getRecipeOutput().copy(), false);
                }
            }
            else
            {
                int burnTime = ForgeHooks.getBurnTime(inventory.getStackInSlot(1));

                if (burnTime > 0)
                {
                    inventory.extractItem(1, 1, false);
                    maxBurnTime = burnTimeLeft = burnTime;
                }
                else
                    cookProgression = 0;
            }
        }

        if (lastBurnState != isBurning())
            world.setBlockState(pos, world.getBlockState(pos).with(AbstractFurnaceBlock.LIT, isBurning()), 3);
    }

    private boolean canSmelt()
    {
        if (!cachedRecipe.isPresent())
            return false;
        if (inventory.getStackInSlot(2).isEmpty())
            return true;

        return ItemUtils.canMergeStacks(cachedRecipe.get().getRecipeOutput(), inventory.getStackInSlot(2));
    }

    private void refreshCachedRecipe()
    {
        cachedRecipe = getWorld().getRecipeManager().getRecipe(IRecipeType.BLASTING, recipeInventory, getWorld());
        isInventoryDirty = false;
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

        isInventoryDirty = true;
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.put("inventory", inventory.serializeNBT());

        compound.putInt("burnTimeLeft", burnTimeLeft);
        compound.putInt("maxBurnTime", maxBurnTime);
        compound.putInt("cookProgression", cookProgression);
        compound.putInt("maxCookProgression", maxCookProgression);
        return super.write(compound);
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ContainerBuilder(Containers.CHONDRITE_BLAST_FURNACE, player)
                .player(player)
                .inventory(10, 86)
                .hotbar(10, 144)
                .tile(this, inventory)
                .slot(0, 58, 19)
                .fuelSlot(1, 58, 55)
                .outputSlot(2, 118, 36)
                .sync()
                .syncInteger(this::getBurnTimeLeft, this::setBurnTimeLeft)
                .syncInteger(this::getMaxBurnTime, this::setMaxBurnTime)
                .syncInteger(this::getCookProgression, this::setCookProgression)
                .syncInteger(this::getMaxCookProgression, this::setMaxCookProgression)
                .create(windowId);
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("container.blast_furnace");
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
        return burnTimeLeft > 0;
    }
}
