package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;

public class SorceryTab extends BaseGrimoireTab
{
    @Override
    public String getTabName()
    {
        return "sorcery";
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.cryptoglyph);
    }
}
