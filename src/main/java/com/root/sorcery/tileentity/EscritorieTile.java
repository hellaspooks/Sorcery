package com.root.sorcery.tileentity;

import com.root.sorcery.container.EscritorieContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class EscritorieTile extends ArcanaVacuumTile implements INamedContainerProvider
{
    public EscritorieTile()
    {
        super(ModTile.ESCRITORIE_TILE);
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
        return new EscritorieContainer(i, world, pos, playerInventory, playerEntity);
    }

}
