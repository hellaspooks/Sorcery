package com.root.sorcery.item;

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
    public static ModItem siderite_ingot;
    public static ModItem sigil_slate;
    public static ModItem sigil_evocation;
    public static ModItem sigil_conjuration;
    public static ModItem sigil_abjuration;
    public static ModItem sigil_enchantment;
    public static ModItem sigil_necromancy;
    public static ModItem sigil_transmutation;

    public static void init(){
        lodestone = new ModItem( "lodestone");
        chondrite_chunk = new ModItem( "chondrite_chunk");
        chondrite_ingot = new ModItem("chondrite_ingot");
        siderite_ingot = new ModItem("siderite_ingot");
        sigil_slate = new ModItem("sigil_slate");
        sigil_evocation = new ModItem("sigil_evocation");
        sigil_conjuration = new ModItem("sigil_conjuration");
        sigil_abjuration = new ModItem("sigil_abjuration");
        sigil_enchantment = new ModItem("sigil_enchantment");
        sigil_necromancy = new ModItem("sigil_necromancy");
        sigil_transmutation = new ModItem("sigil_transmutation");
        
        GeodeItem.init();
        CrystalItem.init();
        ModTools.init();
    }
}
