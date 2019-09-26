package com.root.sorcery.item;

import net.minecraft.item.Item;

import static com.root.sorcery.item.ModGeode.geodeItem;

public class GeodeItem extends ItemBase
{
    public GeodeItem(String registryName)
    {
        super(registryName);
    }
    
    


    public static boolean isGeode(Item item)
    {
        if (geodeItem.getItem() == item)
            return true;
        return false;
    }
}
