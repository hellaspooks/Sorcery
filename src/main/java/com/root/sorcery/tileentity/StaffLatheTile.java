package com.root.sorcery.tileentity;

import com.root.sorcery.container.StaffLatheContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class StaffLatheTile extends TileEntity implements INamedContainerProvider
{

    public StaffLatheTile()
    {
        super(ModTile.STAFF_LATHE_TILE);
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
}
