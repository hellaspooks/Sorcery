package com.root.sorcery.item;

/**
 * Here is where we register the items in the mod.
 * Registration can be handled either here directly, or in sub-classes as seen below with
 * ModGeode, ModCrystal, and ModTools.
 * Note that this split is organizational only, and it doesn't really matter where its registered, as long as it is.
 */
public class ModItem {



    public static ItemBase lodestone;
    public static ItemBase chondrite_chunk;
    public static ItemBase chondrite_ingot;
    public static ItemBase siderite_ingot;
    public static ItemBase sigil_slate;
    public static ItemBase sigil_evocation;
    public static ItemBase sigil_conjuration;
    public static ItemBase sigil_abjuration;
    public static ItemBase sigil_enchantment;
    public static ItemBase sigil_necromancy;
    public static ItemBase sigil_transmutation;


    public static void init(){
        lodestone = new ItemBase( "lodestone");
        chondrite_chunk = new ItemBase( "chondrite_chunk");
        chondrite_ingot = new ItemBase("chondrite_ingot");
        siderite_ingot = new ItemBase("siderite_ingot");
        sigil_slate = new ItemBase("sigil_slate");
        sigil_evocation = new ItemBase("sigil_evocation");
        sigil_conjuration = new ItemBase("sigil_conjuration");
        sigil_abjuration = new ItemBase("sigil_abjuration");
        sigil_enchantment = new ItemBase("sigil_enchantment");
        sigil_necromancy = new ItemBase("sigil_necromancy");
        sigil_transmutation = new ItemBase("sigil_transmutation");

        ModGeode.init();
        ModCrystal.init();
        ModTools.init();
    }
}
