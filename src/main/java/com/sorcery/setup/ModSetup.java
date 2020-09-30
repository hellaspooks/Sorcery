package com.sorcery.setup;

import com.sorcery.item.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup
{

    public static ItemGroup SORCERY = new ItemGroup("Sorcery"){
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItem.GRIMOIRE);
        }
    };

    public static ItemGroup SORCERY_SPELLS = new ItemGroup("Sorcery Spells")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItem.create_water_spell_scroll);
        }
    };
}
