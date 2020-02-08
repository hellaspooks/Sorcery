package com.root.sorcery.client.gui.grimoire.tab;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.voxelindustry.brokkgui.component.GuiNode;
import net.voxelindustry.brokkgui.wrapper.elements.ItemStackView;

import java.util.List;

public interface IGuiTab
{
    List<GuiNode> getElements();

    default ItemStack getIconStack()
    {
        return new ItemStack(Items.APPLE);
    }

    default GuiNode getIcon()
    {
        return new ItemStackView(getIconStack());
    }

    String getName();

    default float getTabOffsetX()
    {
        return 0;
    }

    default void toggleVisibility(boolean isVisible)
    {

    }

    default void setButton(TabButton button)
    {

    }
}
