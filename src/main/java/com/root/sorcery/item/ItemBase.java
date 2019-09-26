package com.root.sorcery.item;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

/**
 * Superclass for all items in the mod.
 * Contains a default constructor that registers each item.
 */
public class ItemBase extends Item
{


    ItemBase(String registryName)
    {
        super(new Item.Properties().group(ModSetup.sorcery));
        this.setRegistryName(registryName);
        Registry.register(Registry.ITEM, registryName, this);

    }
}
