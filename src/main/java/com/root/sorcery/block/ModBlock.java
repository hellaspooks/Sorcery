package com.root.sorcery.block;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder("sorcery")
public class ModBlock
{

    // Simple Blocks
    @ObjectHolder("polished_wolfram")
    public static Block POLISHED_WOLFRAM;

    @ObjectHolder("wolfram_bricks")
    public static Block WOLFRAM_BRICKS;

    @ObjectHolder("runewood_log")
    public static LogBlock RUNEWOOD_LOG;

    @ObjectHolder("stripped_runewood_log")
    public static LogBlock STRIPPED_RUNEWOOD_LOG;

    @ObjectHolder("runewood_planks")
    public static Block RUNEWOOD_PLANKS;

    @ObjectHolder("runewood_leaves")
    public static LeavesBlock RUNEWOOD_LEAVES;


    // Stairs
    @ObjectHolder("wolfram_brick_stairs")
    public static StairsBlock WOLFRAM_BRICK_STAIRS;

    // Slabs
    @ObjectHolder("wolfram_brick_slab")
    public static SlabBlock WOLFRAM_BRICK_SLAB;

    // Walls
    @ObjectHolder("wolfram_brick_wall")
    public static WallBlock WOLFRAM_BRICK_WALL;

    // Decor
    @ObjectHolder("wolfram_lantern")
    public static Block WOLFRAM_LANTERN;

    // Tile Blocks
    @ObjectHolder("alchemical_workbench")
    public static Block ALCHEMICAL_WORKBENCH;

    @ObjectHolder("alchemical_forge")
    public static Block ALCHEMICAL_FORGE;

    @ObjectHolder("reliquary")
    public static Block RELIQUARY;

    @ObjectHolder("wolfram_blast_furnace")
    public static Block WOLFRAM_BLAST_FURNACE;

    @ObjectHolder("monolith_basic")
    public static Block MONOLITH_BASIC;

    @ObjectHolder("monolith_dark")
    public static Block MONOLITH_DARK;

    @ObjectHolder("monolith_solar")
    public static Block MONOLITH_SOLAR;

    @ObjectHolder("monolith_lunar")
    public static Block MONOLITH_LUNAR;

    @ObjectHolder("pylon")
    public static Block PYLON;

    public static void init(RegistryEvent.Register<Block> event)
    {
        // Simple Blocks
        simpleBlockFactory(event, "polished_wolfram", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        simpleBlockFactory(event, "runewood_planks", Material.WOOD, 2.0F, 3.0F, SoundType.WOOD);

        // these two will eventually be tileblocks
        registerBlock(new AlchemicalWorkbenchBlock(), "alchemical_workbench", event);

        simpleBlockFactory(event, "alchemical_forge", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);

        registerBlock(new WolframLanternBlock(), "wolfram_lantern", event);
        registerBlock(new StrippedRunewoodLogBlock(), "stripped_runewood_log", event);
        registerBlock(new LogBlock(MaterialColor.RED, Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F, 2.0F).sound(SoundType.WOOD)), "runewood_log", event);

        registerBlock(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.0F, 1.0F).sound(SoundType.PLANT)), "runewood_leaves", event);


        // Blocks with variations
        WOLFRAM_BRICKS = simpleBlockFactory(event, "wolfram_bricks", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        WOLFRAM_BRICK_SLAB = slabFactory(event, WOLFRAM_BRICKS, "wolfram_brick_slab");
        WOLFRAM_BRICK_WALL = wallFactory(event, WOLFRAM_BRICKS, "wolfram_brick_wall");
        WOLFRAM_BRICK_STAIRS = stairsFactory(event, WOLFRAM_BRICKS, "wolfram_brick_stairs");

        // Tile Blocks
        registerTileBlocks(event, "wolfram_blast_furnace", new WolframBlastFurnaceBlock());
        registerTileBlocks(event, "reliquary", new ReliquaryBlock());
        registerTileBlocks(event, "pylon", new PylonBlock());

        registerTileBlocks(event, "monolith_basic", new MonolithBlock());
        registerTileBlocks(event, "monolith_dark", new MonolithBlock());
        registerTileBlocks(event, "monolith_lunar", new MonolithBlock());
        registerTileBlocks(event, "monolith_solar", new MonolithBlock());

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

    public static void registerTileBlocks(RegistryEvent.Register<Block> event, String registryName, Block block)
    {
        block.setRegistryName(registryName);
        event.getRegistry().register(block);
    }
}
