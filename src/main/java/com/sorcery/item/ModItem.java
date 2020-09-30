package com.sorcery.item;
import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.spell.ModSpell;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


/**
 * Here is where we register the items in the mod.
 * Names in ObjectHolders correspond to resource pack names.
 */
@ObjectHolder("sorcery")
public class ModItem
{
    @ObjectHolder("arcane_dynamo")
    public static Item arcane_dynamo;
    @ObjectHolder("mundane_mechanism")
    public static Item mundane_mechanism;

    // Materials
    @ObjectHolder("lodestone")
    public static Item LODESTONE;
    @ObjectHolder("wolfram_ingot")
    public static Item WOLFRAM_INGOT;
    @ObjectHolder("mythril_ingot")
    public static Item MYTHRIL_INGOT;
    @ObjectHolder("sigil_slate")
    public static Item SIGIL_SLATE;

    // Tomes
    @ObjectHolder("tome_abjuration")
    public static Item TOME_ABJURATION;
    @ObjectHolder("tome_conjuration")
    public static Item TOME_CONJURATION;
    @ObjectHolder("tome_enchantment")
    public static Item TOME_ENCHANTMENT;
    @ObjectHolder("tome_evocation")
    public static Item TOME_EVOCATION;
    @ObjectHolder("tome_necromancy")
    public static Item TOME_NECROMANCY;
    @ObjectHolder("tome_transmutation")
    public static Item TOME_TRANSMUTATION;

    // Sigils
    @ObjectHolder("sigil_evocation")
    public static Item SIGIL_EVOCATION;
    @ObjectHolder("sigil_conjuration")
    public static Item SIGIL_CONJURATION;
    @ObjectHolder("sigil_abjuration")
    public static Item sigil_abjuration;
    @ObjectHolder("sigil_enchantment")
    public static Item sigil_enchantment;
    @ObjectHolder("sigil_necromancy")
    public static Item sigil_necromancy;
    @ObjectHolder("sigil_transmutation")
    public static Item sigil_transmutation;

    // Crystals
    @ObjectHolder("crystal_arcane")
    public static Item crystal_arcane;
    @ObjectHolder("crystal_inert")
    public static Item crystal_inert;
    @ObjectHolder("crystal_carnelian")
    public static Item crystal_carnelian;
    @ObjectHolder("crystal_chalcedony")
    public static Item crystal_chalcedony;
    @ObjectHolder("crystal_sugilite")
    public static Item crystal_sugilite;
    @ObjectHolder("crystal_jasper")
    public static Item crystal_jasper;
    @ObjectHolder("crystal_serpentine")
    public static Item crystal_serpentine;
    @ObjectHolder("crystal_nuummite")
    public static Item crystal_nuummite;

    // Geode
    @ObjectHolder("geode")
    public static Item geode;

    // Crushed Wolframite
    @ObjectHolder("crushed_wolframite")
    public static Item CRUSHED_WOLFRAMITE;

    // Cryptoglyph
    @ObjectHolder("cryptoglyph")
    public static Item cryptoglyph;

    // Grimoire
    @ObjectHolder("grimoire")
    public static Item GRIMOIRE;

    // Spawn Eggs
    @ObjectHolder("toad_spawn_egg")
    public static Item toad_spawn_egg;

    // Staves
    @ObjectHolder("sorcerous_staff")
    public static Item sorcerous_staff;

    // Utility Items
    @ObjectHolder("crystal_resonator")
    public static Item CRYSTAL_RESONATOR;

    // Tools
    @ObjectHolder("wolfram_pickaxe")
    public static PickaxeItem WOLFRAM_PICKAXE;

    @ObjectHolder("mythril_pickaxe")
    public static PickaxeItem MYTHRIL_PICKAXE;

    @ObjectHolder("wolfram_axe")
    public static AxeItem WOLFRAM_AXE;

    @ObjectHolder("mythril_axe")
    public static AxeItem MYTHRIL_AXE;

    @ObjectHolder("wolfram_shovel")
    public static ShovelItem WOLFRAM_SHOVEL;

    @ObjectHolder("mythril_shovel")
    public static ShovelItem MYTHRIL_SHOVEL;

    @ObjectHolder("wolfram_hoe")
    public static HoeItem WOLFRAM_HOE;

    @ObjectHolder("mythril_hoe")
    public static HoeItem MYTHRIL_HOE;


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
    @ObjectHolder("wolfram_fittings")
    public static Item wolfram_fittings;
    @ObjectHolder("mythril_fittings")
    public static Item mythril_fittings;

    //Scrolls
    @ObjectHolder("create_water_spell_scroll")
    public static SpellScrollItem create_water_spell_scroll;

    @ObjectHolder("remove_arcana_spell_scroll")
    public static SpellScrollItem remove_arcana_spell_scroll;

    //Spellbook
    @ObjectHolder("spell_book")
    public static SpellbookItem spell_book;


    public static void init(RegistryEvent.Register<Item> event)
    {
        // Simple Items
        // Materials
        simpleItemFactory("mundane_mechanism", event);
        simpleItemFactory("lodestone", event);
        simpleItemFactory("wolfram_ingot", event);
        simpleItemFactory("mythril_ingot", event);
        simpleItemFactory("sigil_slate", event);
        simpleItemFactory("grimoire", event);
        simpleItemFactory("arcane_dynamo", event);

        // Tomes
        simpleItemFactory("tome_abjuration", event);
        simpleItemFactory("tome_conjuration", event);
        simpleItemFactory("tome_enchantment", event);
        simpleItemFactory("tome_evocation", event);
        simpleItemFactory("tome_necromancy", event);
        simpleItemFactory("tome_transmutation", event);

        // Crystals
        registerItem("crystal_arcane", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_inert", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_carnelian", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_chalcedony", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_sugilite", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_jasper", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_serpentine", new CrystalItem(Constants.ITEM_PROPS), event);
        registerItem("crystal_nuummite", new CrystalItem(Constants.ITEM_PROPS), event);

        // Geode
        registerItem("geode", new GeodeItem(Constants.ITEM_PROPS), event);

        // Crushed Wolframite
        registerItem("crushed_wolframite", new CrushedWolframiteItem(), event);

        // Sigils
        registerItem("sigil_evocation", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_conjuration", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_abjuration", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_enchantment", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_necromancy", new SigilItem(Constants.ITEM_PROPS), event);
        registerItem("sigil_transmutation", new SigilItem(Constants.ITEM_PROPS), event);


        // Spellcasting Items
        registerItem("sorcerous_staff", new StaffItem(Constants.ITEM_PROPS_NONSTACK), event);
        registerItem("spell_book", new SpellbookItem(Constants.ITEM_PROPS_NONSTACK), event);

        // Spell Scrolls
        spellScrollFactory("create_water", event);
        spellScrollFactory("remove_arcana", event);

        // Utility Items
        registerItem("crystal_resonator", new CrystalResonatorItem(Constants.ITEM_PROPS), event);

        // Tools
        // TODO: attack + speed values
        registerItem("wolfram_pickaxe", new PickaxeItem(ItemTier.IRON, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);
        registerItem("mythril_pickaxe", new PickaxeItem(ItemTier.DIAMOND, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);

        registerItem("wolfram_axe", new AxeItem(ItemTier.IRON, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);
        registerItem("mythril_axe", new AxeItem(ItemTier.DIAMOND, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);

        registerItem("wolfram_shovel", new ShovelItem(ItemTier.IRON, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);
        registerItem("mythril_shovel", new ShovelItem(ItemTier.DIAMOND, 1, 1, Constants.ITEM_PROPS_NONSTACK), event);

        registerItem("wolfram_hoe", new HoeItem(ItemTier.IRON, 1, -1.0F,Constants.ITEM_PROPS_NONSTACK), event);
        registerItem("mythril_hoe", new HoeItem(ItemTier.DIAMOND, 1, -1.0F, Constants.ITEM_PROPS_NONSTACK), event);

        // Staff Components

        // Rods
        staffComponentItemFactory("rod", "acacia", event);
        staffComponentItemFactory("rod","birch", event);
        staffComponentItemFactory("rod","dark_oak", event);
        staffComponentItemFactory("rod","jungle", event);
        staffComponentItemFactory("rod","oak", event);
        staffComponentItemFactory("rod","spruce", event);

        // Catalysts
        staffComponentItemFactory("catalyst","initiate", event);
        staffComponentItemFactory("catalyst","apprentice", event);
        staffComponentItemFactory("catalyst","magician", event);
        staffComponentItemFactory("catalyst","archmage", event);

        // Fittings
        staffComponentItemFactory("fittings","iron", event);
        staffComponentItemFactory("fittings","gold", event);
        staffComponentItemFactory("fittings","wolfram", event);
        staffComponentItemFactory("fittings","mythril", event);

        // Register Block Items
        // Simple Blocks
        blockItemFactory(ModBlock.POLISHED_WOLFRAM.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNEWOOD_LOG.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.STRIPPED_RUNEWOOD_LOG.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNEWOOD_LEAVES.get(), Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.RUNEWOOD_SAPLING.get(), Constants.ITEM_PROPS, event);

        // Blocks with variations
        blockItemFactory(ModBlock.RUNESTONE_BRICKS.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNESTONE_BRICK_SLAB.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNESTONE_BRICK_STAIRS.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNESTONE_BRICK_WALL.get(), Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.RUNEWOOD_PLANKS.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNEWOOD_PLANK_SLAB.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNEWOOD_PLANK_STAIRS.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.RUNEWOOD_PLANK_FENCE.get(), Constants.ITEM_PROPS, event);



        // Blocks with tile entities

        blockItemFactory(ModBlock.MONOLITH_CHISELED.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_SOLAR.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_LUNAR.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.MONOLITH_DARK.get(), Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.CHISELED_RUNESTONE.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.DARK_RUNESTONE.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.LAPIS_RUNESTONE.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.LUNAR_RUNESTONE.get(), Constants.ITEM_PROPS, event);
        blockItemFactory(ModBlock.SOLAR_RUNESTONE.get(), Constants.ITEM_PROPS, event);


        blockItemFactory(ModBlock.WOLFRAM_LANTERN.get(), Constants.ITEM_PROPS, event);

        blockItemFactory(ModBlock.PYLON.get(), Constants.ITEM_PROPS, event);

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

    public static void staffComponentItemFactory(String componentType, String componentName, RegistryEvent.Register event)
    {
        StaffComponentItem item = new StaffComponentItem(Constants.ITEM_PROPS, componentName, 50);
        item.setRegistryName(componentName + "_" + componentType);
        event.getRegistry().register(item);
    }

    public static void spellScrollFactory(String spellName, RegistryEvent.Register event)
    {
        ResourceLocation spellLoc = new ResourceLocation(Constants.MODID, spellName + "_spell");
        registerItem(spellName + "_spell_scroll", new SpellScrollItem(Constants.ITEM_PROPS_SCROLLS, spellLoc), event);
    }

}
