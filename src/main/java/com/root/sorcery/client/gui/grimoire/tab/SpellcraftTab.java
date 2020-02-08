package com.root.sorcery.client.gui.grimoire.tab;

import com.root.sorcery.item.ModItem;
import net.minecraft.item.ItemStack;

public class SpellcraftTab extends BaseGrimoireTab
{
    @Override
    public String getTabName()
    {
        return "spellcraft";
    }

    @Override
    public ItemStack getIconStack()
    {
        return new ItemStack(ModItem.initiate_catalyst);
    }
}
