package com.root.sorcery.item;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

/**
 * Here is where we register the items in the mod.
 * Registration can be handled either here directly, or in sub-classes as seen below with
 * ModGeode, ModCrystal, and ModTool.
 * Note that this split is organizational only, and it doesn't really matter where its registered, as long as it is.
 */
public class ModItem
{



    // Materials
    public static Item lodestone;
    public static Item chondrite_chunk;
    public static Item chondrite_ingot;
    public static Item siderite_ingot;
    public static Item sigil_slate;

    // Sigils
    public static Item sigil_evocation;
    public static Item sigil_conjuration;
    public static Item sigil_abjuration;
    public static Item sigil_enchantment;
    public static Item sigil_necromancy;
    public static Item sigil_transmutation;


    // Crystals
    public static Item carnelian;
    public static Item chalcedony;
    public static Item sugilite;
    public static Item jasper;
    public static Item serpentine;
    public static Item nuummite;

    // Geode
    public static Item geode;

    public static void init()
    {
        // Materials
        lodestone = itemFactory(ItemEnum.MATERIAL, "lodestone");
        chondrite_chunk = itemFactory(ItemEnum.MATERIAL, "chondrite_chunk");
        chondrite_ingot = itemFactory(ItemEnum.MATERIAL, "chondrite_ingot");
        siderite_ingot = itemFactory(ItemEnum.MATERIAL, "siderite_ingot");
        sigil_slate = itemFactory(ItemEnum.MATERIAL, "sigil_slate");

        // Sigils
        sigil_evocation = itemFactory(ItemEnum.SIGIL, "sigil_evocation");
        sigil_conjuration = itemFactory(ItemEnum.SIGIL,"sigil_conjuration");
        sigil_abjuration = itemFactory(ItemEnum.SIGIL,"sigil_abjuration");
        sigil_enchantment = itemFactory(ItemEnum.SIGIL,"sigil_enchantment");
        sigil_necromancy = itemFactory(ItemEnum.SIGIL,"sigil_necromancy");
        sigil_transmutation = itemFactory(ItemEnum.SIGIL,"sigil_transmutation");

        // Crystals
        carnelian = itemFactory(ItemEnum.CRYSTAL,"carnelian");
        chalcedony = itemFactory(ItemEnum.CRYSTAL, "chalcedony");
        sugilite = itemFactory(ItemEnum.CRYSTAL,"sugilite");
        jasper = itemFactory(ItemEnum.CRYSTAL,"jasper");
        serpentine = itemFactory(ItemEnum.CRYSTAL,"serpentine");
        nuummite = itemFactory(ItemEnum.CRYSTAL,"nuummite");

        // Geode
        geode = itemFactory(ItemEnum.GEODE, "geode");

    }

    public static Item itemFactory(ItemEnum itemEnum, String registryName)
    {
        Item item = null;
        switch (itemEnum)
        {
            case GEODE:
                item = new GeodeItem();
                break;
            case CRYSTAL:
                item = new CrystalItem();
                break;
            case SIGIL:
                item = new SigilItem();
                break;
            case MATERIAL:
                item = new MaterialItem();
                break;
            default:
                // Error
                break;
        }

        item.setRegistryName(registryName);
        Registry.register(Registry.ITEM, registryName, item);

        return item;

    }

}
