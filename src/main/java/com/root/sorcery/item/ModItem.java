package com.root.sorcery.item;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


/**
 * Here is where we register the items in the mod.
 * Registration can be handled either here directly, or in sub-classes as seen below with
 * ModGeode, ModCrystal, and ModTool.
 * Note that this split is organizational only, and it doesn't really matter where its registered, as long as it is.
 */
public class ModItem
{



    // Materials
    @ObjectHolder("sorcery:lodestone")
    public static Item lodestone;

    @ObjectHolder("sorcery:chondrite_chunk")
    public static Item chondrite_chunk;

    @ObjectHolder("sorcery:chondrite_ingot")
    public static Item chondrite_ingot;

    @ObjectHolder("sorcery:siderite_ingot")
    public static Item siderite_ingot;

    @ObjectHolder("sorcery:sigil_slate")
    public static Item sigil_slate;

    // Sigils
    @ObjectHolder("sorcery:sigil_evocation")
    public static SigilItem sigil_evocation;
    @ObjectHolder("sorcery:sigil_conjuration")
    public static Item sigil_conjuration;
    @ObjectHolder("sorcery:sigil_abjuration")
    public static Item sigil_abjuration;
    @ObjectHolder("sorcery:sigil_enchantment")
    public static Item sigil_enchantment;
    @ObjectHolder("sorcery:sigil_necromancy")
    public static Item sigil_necromancy;
    @ObjectHolder("sorcery:sigil_transmutation")
    public static Item sigil_transmutation;


    // Crystals
    @ObjectHolder("sorcery:carnelian")
    public static Item carnelian;
    @ObjectHolder("sorcery:chalcedony")
    public static Item chalcedony;
    @ObjectHolder("sorcery:sugilite")
    public static Item sugilite;
    @ObjectHolder("sorcery:jasper")
    public static Item jasper;
    @ObjectHolder("sorcery:serpentine")
    public static Item serpentine;
    @ObjectHolder("sorcery:nuummite")
    public static Item nuummite;

    // Geode
    @ObjectHolder("sorcery:geode")
    public static Item geode;

    public static void init(RegistryEvent.Register<Item> event)
    {
        // Materials
        lodestone = itemFactory(ItemEnum.MATERIAL, "lodestone", event);
        chondrite_chunk = itemFactory(ItemEnum.MATERIAL, "chondrite_chunk", event);
        chondrite_ingot = itemFactory(ItemEnum.MATERIAL, "chondrite_ingot", event);
        siderite_ingot = itemFactory(ItemEnum.MATERIAL, "siderite_ingot", event);
        sigil_slate = itemFactory(ItemEnum.MATERIAL, "sigil_slate", event);

        // Sigils
        sigil_evocation = new SigilItem();
        sigil_evocation.setRegistryName("sigil_evocation");
        event.getRegistry().register(sigil_evocation);
        sigil_conjuration = itemFactory(ItemEnum.SIGIL,"sigil_conjuration", event);
        sigil_abjuration = itemFactory(ItemEnum.SIGIL,"sigil_abjuration", event);
        sigil_enchantment = itemFactory(ItemEnum.SIGIL,"sigil_enchantment", event);
        sigil_necromancy = itemFactory(ItemEnum.SIGIL,"sigil_necromancy", event);
        sigil_transmutation = itemFactory(ItemEnum.SIGIL,"sigil_transmutation", event);

        // Crystals
        carnelian = itemFactory(ItemEnum.CRYSTAL,"carnelian", event);
        chalcedony = itemFactory(ItemEnum.CRYSTAL, "chalcedony", event);
        sugilite = itemFactory(ItemEnum.CRYSTAL,"sugilite", event);
        jasper = itemFactory(ItemEnum.CRYSTAL,"jasper", event);
        serpentine = itemFactory(ItemEnum.CRYSTAL,"serpentine", event);
        nuummite = itemFactory(ItemEnum.CRYSTAL,"nuummite", event);

        // Geode
        geode = itemFactory(ItemEnum.GEODE, "geode", event);

        // Register Block Items
        Item.Properties properties = new Item.Properties().group(ModSetup.sorcery);

        // Simple Blocks
        blockItemFactory(ModBlock.CHONDRITE_BRICKS, properties, event);
        blockItemFactory(ModBlock.POLISHED_CHONDRITE, properties, event);

        // Slabs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_SLAB, properties, event);

        // Stairs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_STAIRS, properties, event);

        // Walls
        blockItemFactory(ModBlock.CHONDRITE_BRICK_WALL, properties, event);

        // Blocks with tile entities
        blockItemFactory(ModBlock.RELIQUARY, properties, event);
        blockItemFactory(ModBlock.CHONDRITE_BLAST_FURNACE, properties, event);

        blockItemFactory(ModBlock.MONOLITH_NORMAL, properties, event);
        blockItemFactory(ModBlock.MONOLITH_SOLAR, properties, event);
        blockItemFactory(ModBlock.MONOLITH_LUNAR, properties, event);
        blockItemFactory(ModBlock.MONOLITH_DARK, properties, event);
    }

    public static Item itemFactory(ItemEnum itemEnum, String registryName, RegistryEvent.Register<Item> event)
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
        event.getRegistry().register(item);

        return item;
    }

    public static void blockItemFactory(Block block, Item.Properties properties, RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new BlockItem(block, properties).setRegistryName(block.getRegistryName()));
    }

}
