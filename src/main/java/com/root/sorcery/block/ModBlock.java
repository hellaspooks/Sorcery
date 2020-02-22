package com.root.sorcery.block;

import com.root.sorcery.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder("sorcery")
public class ModBlock
{

    // Simple Blocks
    @ObjectHolder("polished_wolfram")
    public static Block POLISHED_WOLFRAM;

    @ObjectHolder("runestone_bricks")
    public static Block RUNESTONE_BRICKS;

    @ObjectHolder("runewood_log")
    public static LogBlock RUNEWOOD_LOG;

    @ObjectHolder("stripped_runewood_log")
    public static StrippedRunewoodLogBlock STRIPPED_RUNEWOOD_LOG;

    @ObjectHolder("runewood_planks")
    public static Block RUNEWOOD_PLANKS;

    @ObjectHolder("runewood_leaves")
    public static LeavesBlock RUNEWOOD_LEAVES;

    @ObjectHolder("wolframite_ore")
    public static OreBlock WOLFRAMITE_ORE;


    // Stairs
    @ObjectHolder("runestone_brick_stairs")
    public static StairsBlock RUNESTONE_BRICK_STAIRS;

    @ObjectHolder("runewood_plank_stairs")
    public static StairsBlock RUNEWOOD_PLANK_STAIRS;

    // Slabs
    @ObjectHolder("runestone_brick_slab")
    public static SlabBlock RUNESTONE_BRICK_SLAB;

    @ObjectHolder("runewood_plank_slab")
    public static SlabBlock RUNEWOOD_PLANK_SLAB;

    // Walls
    @ObjectHolder("runestone_brick_wall")
    public static WallBlock RUNESTONE_BRICK_WALL;

    @ObjectHolder("runewood_plank_fence")
    public static FenceBlock RUNEWOOD_PLANK_FENCE;

    // Decor
    @ObjectHolder("wolfram_lantern")
    public static Block WOLFRAM_LANTERN;

    // Monoliths
    @ObjectHolder("monolith_chiseled")
    public static Block MONOLITH_CHISELED;

    @ObjectHolder("monolith_dark")
    public static Block MONOLITH_DARK;

    @ObjectHolder("monolith_lapis")
    public static Block MONOLITH_LAPIS;

    @ObjectHolder("monolith_lunar")
    public static Block MONOLITH_LUNAR;

    @ObjectHolder("monolith_solar")
    public static Block MONOLITH_SOLAR;


    // Runestones
    @ObjectHolder("chiseled_runestone")
    public static Block CHISELED_RUNESTONE;

    @ObjectHolder("dark_runestone")
    public static RunestoneBlock DARK_RUNESTONE;

    @ObjectHolder("lapis_runestone")
    public static RunestoneBlock LAPIS_RUNESTONE;

    @ObjectHolder("solar_runestone")
    public static RunestoneBlock SOLAR_RUNESTONE;

    @ObjectHolder("lunar_runestone")
    public static RunestoneBlock LUNAR_RUNESTONE;

    // Misc Tile Blocks
    @ObjectHolder("alchemical_workbench")
    public static Block ALCHEMICAL_WORKBENCH;

    @ObjectHolder("alchemical_forge")
    public static Block ALCHEMICAL_FORGE;

    @ObjectHolder("reliquary")
    public static Block RELIQUARY;

    @ObjectHolder("wolfram_blast_furnace")
    public static Block WOLFRAM_BLAST_FURNACE;

    @ObjectHolder("pylon")
    public static Block PYLON;

    @ObjectHolder("staff_lathe")
    public static Block STAFF_LATHE;

    @ObjectHolder("runewood_sapling")
    public static Block RUNEWOOD_SAPLING;

    public static void init(RegistryEvent.Register<Block> event)
    {
        // Simple Blocks
        simpleBlockFactory(event, "polished_wolfram", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        simpleBlockFactory(event, "alchemical_forge", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);

        // Ore Blocks
        oreBlockFactory(event, "wolframite_ore", Material.ROCK, 3.0F, 3.0F, SoundType.STONE);

        // non-simple blocks
        registerBlock(new WolframLanternBlock(), "wolfram_lantern", event);
        registerBlock(new StrippedRunewoodLogBlock(), "stripped_runewood_log", event);
        registerBlock(new RunewoodLogBlock(), "runewood_log", event);
        registerBlock(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT)), "runewood_leaves", event);
        registerBlock(new AlchemicalWorkbenchBlock(), "alchemical_workbench", event);
        registerBlock(new RunewoodSaplingBlock(), "runewood_sapling", event);

        // Blocks with variations
        RUNESTONE_BRICKS = simpleBlockFactory(event, "runestone_bricks", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        RUNESTONE_BRICK_SLAB = slabFactory(event, RUNESTONE_BRICKS, "runestone_brick_slab");
        RUNESTONE_BRICK_WALL = wallFactory(event, RUNESTONE_BRICKS, "runestone_brick_wall");
        RUNESTONE_BRICK_STAIRS = stairsFactory(event, RUNESTONE_BRICKS, "runestone_brick_stairs");

        RUNEWOOD_PLANKS = simpleBlockFactory(event, "runewood_planks", Material.WOOD, 2.0F, 3.0F, SoundType.WOOD);
        RUNEWOOD_PLANK_SLAB = slabFactory(event, RUNEWOOD_PLANKS, "runewood_plank_slab");
        RUNEWOOD_PLANK_FENCE = fenceFactory(event, RUNEWOOD_PLANKS, "runewood_plank_fence");
        RUNEWOOD_PLANK_STAIRS = stairsFactory(event, RUNEWOOD_PLANKS, "runewood_plank_stairs");

        // Monolith "blocks"
        registerBlock(new MonolithBlock(), "monolith_chiseled", event);
        registerBlock(new DarkMonolithBlock(), "monolith_dark", event);
        registerBlock(new LapisMonolithBlock(), "monolith_lapis", event);
        registerBlock(new LunarMonolithBlock(), "monolith_lunar", event);
        registerBlock(new SolarMonolithBlock(), "monolith_solar", event);

        // Runestone blocks
        registerBlock(new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_chiseled")), "chiseled_runestone", event);
        registerBlock(new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_dark")), "dark_runestone", event);
        registerBlock(new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_lapis")), "lapis_runestone", event);
        registerBlock(new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_lunar")), "lunar_runestone", event);
        registerBlock(new RunestoneBlock(new ResourceLocation(Constants.MODID, "monolith_solar")), "solar_runestone", event);


        // Tile Blocks
        registerBlock(new WolframBlastFurnaceBlock(), "wolfram_blast_furnace", event);
        registerBlock(new ReliquaryBlock(), "reliquary", event);
        registerBlock(new PylonBlock(), "pylon", event);
        registerBlock(new StaffLatheBlock(), "staff_lathe", event);
    }

    public static Block simpleBlockFactory(RegistryEvent.Register<Block> event, String registryName, Material material, Float hardness, Float resistance, SoundType sound)
    {
        Block block = new Block(Block.Properties
                .create(material)
                .hardnessAndResistance(hardness, resistance)
                .sound(sound));

        block.setRegistryName(registryName);
        event.getRegistry().register(block);

        return block;
    }

    public static OreBlock oreBlockFactory(RegistryEvent.Register<Block> event, String registryName, Material material, Float hardness, Float resistance, SoundType sound)
    {
        OreBlock block = new OreBlock(Block.Properties
                .create(material)
                .hardnessAndResistance(hardness, resistance)
                .sound(sound));

        block.setRegistryName(registryName);
        event.getRegistry().register(block);

        return block;
    }


    public static void registerBlock(Block block, String registryName, RegistryEvent.Register<Block> event)
    {
        block.setRegistryName(registryName);
        event.getRegistry().register(block);
    }

    public static SlabBlock slabFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        SlabBlock slabBlock = new SlabBlock(Block.Properties.from(block));

        slabBlock.setRegistryName(registryName);

        event.getRegistry().register(slabBlock);

        return slabBlock;
    }

    public static WallBlock wallFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        WallBlock wallBlock = new WallBlock(Block.Properties.from(block));

        wallBlock.setRegistryName(registryName);
        event.getRegistry().register(wallBlock);

        return wallBlock;
    }

    public static StairsBlock stairsFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        StairsBlock stairsBlock = new StairMod(block);

        stairsBlock.setRegistryName(registryName);
        event.getRegistry().register(stairsBlock);

        return stairsBlock;
    }

    public static FenceBlock fenceFactory(RegistryEvent.Register<Block> event, Block block, String registryName)
    {
        FenceBlock fenceBlock = new FenceBlock(Block.Properties.from(block));

        fenceBlock.setRegistryName(registryName);
        event.getRegistry().register(fenceBlock);

        return fenceBlock;
    }
}
