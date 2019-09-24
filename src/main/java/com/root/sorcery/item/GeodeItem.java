package com.root.sorcery.item;

import net.minecraft.item.Item;

public class GeodeItem extends ModItem {
    private GeodeItem(String registryName) {
        super(registryName);
    }
    
    
    public static GeodeItem geodeItem;
    
    public static void init(){
        geodeItem = new GeodeItem("geodeItem");
    }

    public static boolean isGeode(Item item) {
        if (geodeItem.getItem() == item)
            return true;
        return false;
    }
}
