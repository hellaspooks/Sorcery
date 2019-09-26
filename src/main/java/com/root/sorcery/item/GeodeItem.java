package com.root.sorcery.item;

import net.minecraft.item.Item;

import static com.root.sorcery.item.ModItem.geode;

public class GeodeItem extends ItemMod
{
    public GeodeItem()
    {
        super();
    }
    
    


    public static boolean isGeode(Item item)
    {
        if (geode.getItem() == item)
            return true;
        return false;
    }
}
