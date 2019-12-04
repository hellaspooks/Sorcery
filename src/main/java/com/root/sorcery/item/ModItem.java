package com.root.sorcery.item;
import com.root.sorcery.Constants;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.entity.ModEntity;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


/**
 * Here is where we register the items in the mod.
 * Names in ObjectHolders correspond to resource pack names.
 */
@ObjectHolder("sorcery")
public class ModItem
{
    // Materials
    @ObjectHolder("lodestone")
    public static Item lodestone;

    @ObjectHolder("chondrite_chunk")
    public static Item chondrite_chunk;

    @ObjectHolder("chondrite_ingot")
    public static Item chondrite_ingot;

    @ObjectHolder("siderite_ingot")
    public static Item siderite_ingot;

    @ObjectHolder("sigil_slate")
    public static Item sigil_slate;

    // Sigils
    @ObjectHolder("sigil_evocation")
    public static Item sigil_evocation;
    @ObjectHolder("sigil_conjuration")
    public static Item sigil_conjuration;
    @ObjectHolder("sigil_abjuration")
    public static Item sigil_abjuration;
    @ObjectHolder("sigil_enchantment")
    public static Item sigil_enchantment;
    @ObjectHolder("sigil_necromancy")
    public static Item sigil_necromancy;
    @ObjectHolder("sigil_transmutation")
    public static Item sigil_transmutation;


    // Crystals
    @ObjectHolder("carnelian")
    public static Item carnelian;
    @ObjectHolder("chalcedony")
    public static Item chalcedony;
    @ObjectHolder("sugilite")
    public static Item sugilite;
    @ObjectHolder("jasper")
    public static Item jasper;
    @ObjectHolder("serpentine")
    public static Item serpentine;
    @ObjectHolder("nuummite")
    public static Item nuummite;

    // Geode
    @ObjectHolder("geode")
    public static Item geode;

    // Arcanomicon
    @ObjectHolder("cryptoglyph")
    public static Item cryptoglyph;

    // Spawn Eggs
    @ObjectHolder("toad_spawn_egg")
    public static Item toad_spawn_egg;

    // Staves
    @ObjectHolder("sorcerous_staff")
    public static Item sorcerous_staff;

    // Utility Items
    @ObjectHolder("linking_stick")
    public static Item linking_stick;

    // Staff Components
    // Rods
    @ObjectHolder("acacia_rod")
    public static Item acacia_rod;
    @ObjectHolder("birch_rod")
    public static Item birch_rod;
    @ObjectHolder("dark_oak_rod")
    public static Item dark_oak_rod;
    @ObjectHolder("jungle_rod")
    public static Item jungle_rod;
    @ObjectHolder("oak_rod")
    public static Item oak_rod;
    @ObjectHolder("spruce_rod")
    public static Item spruce_rod;

    // Catalysts
    @ObjectHolder("initiate_catalyst")
    public static Item initiate_catalyst;
    @ObjectHolder("apprentice_catalyst")
    public static Item apprentice_catalyst;
    @ObjectHolder("magician_catalyst")
    public static Item magician_catalyst;
    @ObjectHolder("archmage_catalyst")
    public static Item archmage_catalyst;

    // Fittings
    @ObjectHolder("iron_fittings")
    public static Item iron_fittings;
    @ObjectHolder("gold_fittings")
    public static Item gold_fittings;
    @ObjectHolder("chondrite_fittings")
    public static Item chondrite_fittings;
    @ObjectHolder("siderite_fittings")
    public static Item siderite_fittings;



    public static void init(RegistryEvent.Register<Item> event)
    {
        // Simple Items
        // Materials
        simpleItemFactory("lodestone", event);
        simpleItemFactory("chondrite_chunk", event);
        simpleItemFactory("chondrite_ingot", event);
        simpleItemFactory("siderite_ingot", event);
        simpleItemFactory("sigil_slate", event);


        // Crystals
        registerItem("carnelian", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem( "chalcedony", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("sugilite", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("jasper", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("serpentine", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("nuummite", new CrystalItem(Constants.ITEM_PROPS), event);

        // Geode
        registerItem( "geode", new GeodeItem(Constants.ITEM_PROPS), event);

        // Sigils
        registerItem("sigil_evocation", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_conjuration", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_abjuration", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_enchantment", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_necromancy", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_transmutation", new SigilItem(Constants.ITEM_PROPS), event);

        // Arcanomicon
        registerItem("cryptoglyph", new SpellGrantingItem(Constants.ITEM_PROPS), event);

        //Spawn Eggs
        registerItem("toad_spawn_egg", new SpawnEggItem(ModEntity.TOAD, 4470310, 10592673, Constants.ITEM_PROPS), event);

        // Staves
        registerItem("sorcerous_staff", new StaffItem(Constants.ITEM_PROPS), event);

        // Utility Items
        registerItem("linking_stick", new LinkingItem(Constants.ITEM_PROPS), event);

        // Staff Components

        // Rods
        registerItem("acacia_rod", new Item(Constants.ITEM_PROPS), event);
        registerItem("birch_rod", new Item(Constants.ITEM_PROPS), event);
        registerItem("dark_oak_rod", new Item(Constants.ITEM_PROPS), event);
        registerItem("jungle_rod", new Item(Constants.ITEM_PROPS), event);
        registerItem("oak_rod", new Item(Constants.ITEM_PROPS), event);
        registerItem("spruce_rod", new Item(Constants.ITEM_PROPS), event);

        // Catalysts
        registerItem("initiate_catalyst", new Item(Constants.ITEM_PROPS), event);
        registerItem("apprentice_catalyst", new Item(Constants.ITEM_PROPS), event);
        registerItem("magician_catalyst", new Item(Constants.ITEM_PROPS), event);
        registerItem("archmage_catalyst", new Item(Constants.ITEM_PROPS), event);

        // Fittings
        registerItem("iron_fittings", new Item(Constants.ITEM_PROPS), event);
        registerItem("gold_fittings", new Item(Constants.ITEM_PROPS), event);
        registerItem("chondrite_fittings", new Item(Constants.ITEM_PROPS), event);
        registerItem("siderite_fittings", new Item(Constants.ITEM_PROPS), event);

        // Register Block Items
        // Simple Blocks
        blockItemFactory(ModBlock.CHONDRITE_BRICKS, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.POLISHED_CHONDRITE, Constants.ITEM_PROPS, event);

        // Slabs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_SLAB, Constants.ITEM_PROPS, event);

        // Stairs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_STAIRS, Constants.ITEM_PROPS, event);

        // Walls
        blockItemFactory(ModBlock.CHONDRITE_BRICK_WALL, Constants.ITEM_PROPS, event);


        // Blocks with tile entities
        blockItemFactory(ModBlock.RELIQUARY, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.CHONDRITE_BLAST_FURNACE, Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.MONOLITH_NORMAL, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_SOLAR, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_LUNAR, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_DARK, Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.CHONDRITE_LANTERN, Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.PYLON, Constants.ITEM_PROPS, event);
    }

    public static void simpleItemFactory(String registryName, RegistryEvent.Register<Item> event)
    {
        Item item = new Item(Constants.ITEM_PROPS);
        item.setRegistryName(registryName);
        event.getRegistry().register(item);
    }

    public static void registerItem(String registryName, Item item, RegistryEvent.Register<Item> event)
    {
        item.setRegistryName(registryName);
        event.getRegistry().register(item);
    }

    public static void blockItemFactory(Block block, Item.Properties properties, RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new BlockItem(block, properties).setRegistryName(block.getRegistryName()));
    }

}
