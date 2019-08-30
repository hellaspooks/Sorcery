package com.root.sorcery.items;

import net.minecraft.item.Item;

public class Geode extends ModItem {
    private Geode(String registryName) {
        super(registryName);
    }
    
    
    public static Geode geode;
    
    public static void init(){
        geode = new Geode("geode");
    }

    public static boolean isGeode(Item item) {
        if (geode.getItem() == item)
            return true;
        return false;
    }
}
