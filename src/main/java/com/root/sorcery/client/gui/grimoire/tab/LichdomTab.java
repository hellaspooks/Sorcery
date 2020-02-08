package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;

public class LichdomTab extends BaseGrimoireTab
{
    @Override
    public String getTabName()
    {
        return "lichdom";
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.apprentice_catalyst);
    }
}
