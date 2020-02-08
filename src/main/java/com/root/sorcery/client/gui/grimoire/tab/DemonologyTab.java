package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;

public class DemonologyTab extends BaseGrimoireTab
{
    @Override
    public String getTabName()
    {
        return "demonology";
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.archmage_catalyst);
    }
}
