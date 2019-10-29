package com.root.sorcery.item;
import com.root.sorcery.block.ModBlock;
import com.root.sorcery.entity.ModEntity;
import com.root.sorcery.setup.ModSetup;
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
public class ModItem
{

    public static final Item.Properties ITEM_PROPS = new Item.Properties().group(ModSetup.sorcery);



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
    public static Item sigil_evocation;
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

    // Arcanomicon
    @ObjectHolder("sorcery:cryptoglyph")
    public static Item cryptoglyph;

    // Spawn Eggs
    @ObjectHolder("sorcery:toad_spawn_egg")
    public static Item toad_spawn_egg;

    // Staves

    @ObjectHolder("sorcery:initiate_staff")
    public static Item initiate_staff;

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
        registerItem("carnelian", new CrystalItem(ITEM_PROPS), event);
        registerItem( "chalcedony", new CrystalItem(ITEM_PROPS), event);
        registerItem("sugilite", new CrystalItem(ITEM_PROPS), event);
        registerItem("jasper", new CrystalItem(ITEM_PROPS), event);
        registerItem("serpentine", new CrystalItem(ITEM_PROPS), event);
        registerItem("nuummite", new CrystalItem(ITEM_PROPS), event);

        // Geode
        registerItem( "geode", new GeodeItem(ITEM_PROPS), event);

        // Sigils
        registerItem("sigil_evocation", new SigilItem(ITEM_PROPS), event);
        registerItem("sigil_conjuration", new SigilItem(ITEM_PROPS), event);
        registerItem("sigil_abjuration", new SigilItem(ITEM_PROPS), event);
        registerItem("sigil_enchantment", new SigilItem(ITEM_PROPS), event);
        registerItem("sigil_necromancy", new SigilItem(ITEM_PROPS), event);
        registerItem("sigil_transmutation", new SigilItem(ITEM_PROPS), event);

        // Arcanomicon
        registerItem("cryptoglyph", new SpellGrantingItem(ITEM_PROPS), event);

        //Spawn Eggs
        registerItem("toad_spawn_egg", new SpawnEggItem(ModEntity.TOAD, 4470310, 10592673, ITEM_PROPS), event);

        // Staffs
        registerItem("initiate_staff", new StaffItem(ITEM_PROPS), event);

        // Register Block Items

        // Simple Blocks
        blockItemFactory(ModBlock.CHONDRITE_BRICKS, ITEM_PROPS, event);
        blockItemFactory(ModBlock.POLISHED_CHONDRITE, ITEM_PROPS, event);

        // Slabs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_SLAB, ITEM_PROPS, event);

        // Stairs
        blockItemFactory(ModBlock.CHONDRITE_BRICK_STAIRS, ITEM_PROPS, event);

        // Walls
        blockItemFactory(ModBlock.CHONDRITE_BRICK_WALL, ITEM_PROPS, event);


        // Blocks with tile entities
        blockItemFactory(ModBlock.RELIQUARY, ITEM_PROPS, event);
        blockItemFactory(ModBlock.CHONDRITE_BLAST_FURNACE, ITEM_PROPS, event);

        blockItemFactory(ModBlock.MONOLITH_NORMAL, ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_SOLAR, ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_LUNAR, ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_DARK, ITEM_PROPS, event);
        blockItemFactory(ModBlock.CHONDRITE_LANTERN, ITEM_PROPS, event);
    }

    public static void simpleItemFactory(String registryName, RegistryEvent.Register<Item> event)
    {
        Item item = new Item(ITEM_PROPS);
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
