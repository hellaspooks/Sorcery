package com.root.sorcery.client.gui;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.voxelindustry.steamlayer.container.BuiltContainer;
import net.voxelindustry.steamlayer.tile.TileBase;

public abstract class BaseGuiContainer<T extends TileBase> extends ContainerScreen<BuiltContainer>
{
    private T tile;

    public BaseGuiContainer(BuiltContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);

        tile = (T) screenContainer.getMainTile();
    }

    public T getTile()
    {
        return tile;
    }
}
