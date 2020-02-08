package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;

public class EtherealTab extends BaseGrimoireTab
{
    @Override
    public String getTabName()
    {
        return "ethereal";
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.chalcedony);
    }
}
