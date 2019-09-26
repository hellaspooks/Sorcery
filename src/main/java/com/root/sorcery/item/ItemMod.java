package com.root.sorcery.item;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;

/**
 * Superclass for all items in the mod.
 * Contains a default constructor that registers each item.
 */
public class ItemMod extends Item
{

    ItemMod()
    {
        super(new Item.Properties().group(ModSetup.sorcery));

    }
}
