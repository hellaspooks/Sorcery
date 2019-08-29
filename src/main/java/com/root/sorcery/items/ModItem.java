package com.root.sorcery.items;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItem extends Item {

    ModItem(String registryName){
        super(new Item.Properties().group(ModSetup.sorcery));
        this.setRegistryName(registryName);
        Registry.register(Registry.ITEM, registryName, this);

    }

    public static ModItem lodestone;
    public static ModItem chondrite_chunk;
    public static ModItem chondrite_ingot;

    public static void init(){
        lodestone = new ModItem( "lodestone");
        chondrite_chunk = new ModItem( "chondrite_chunk");
        chondrite_ingot = new ModItem("chondrite_ingot");
        
        Geode.init();
        Crystals.init();
    }
}
