package com.root.sorcery.block;

import com.root.sorcery.setup.ModSetup;
import com.root.sorcery.tileentity.ChondriteBlastFurnaceTileEntity;
import com.root.sorcery.tileentity.ReliquaryTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;


public class ModBlock
{

    // Simple Blocks
    @ObjectHolder("sorcery:polished_chondrite")
    public static Block POLISHED_CHONDRITE;

    @ObjectHolder("sorcery:chondrite_bricks")
    public static Block CHONDRITE_BRICKS;

    // Stairs
    @ObjectHolder("sorcery:chondrite_brick_stairs")
    public static StairsBlock CHONDRITE_BRICK_STAIRS;

    // Slabs
    @ObjectHolder("sorcery:chondrite_brick_slab")
    public static SlabBlock CHONDRITE_BRICK_SLAB;

    // Walls
    @ObjectHolder("sorcery:chondrite_brick_wall")
    public static WallBlock CHONDRITE_BRICK_WALL;

    // Tile Blocks
    @ObjectHolder("sorcery:reliquary")
    public static Block RELIQUARY;

    @ObjectHolder("sorcery:reliquary")
    public static TileEntityType<ReliquaryTileEntity> RELIQUARY_TILE;

    @ObjectHolder("sorcery:chondrite_blast_furnace")
    public static Block CHONDRITE_BLAST_FURNACE;

    @ObjectHolder("sorcery:chondrite_blast_furnace")
    public static TileEntityType<ChondriteBlastFurnaceTileEntity> CHONDRITE_BLAST_FURNACE_TILE;


    public static void init(RegistryEvent.Register<Block> event)
    {
        // Simple Blocks
        POLISHED_CHONDRITE = new BlockMod("polished_chondrite", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        CHONDRITE_BRICKS = new BlockMod("chondrite_bricks", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);

        // Slabs
        CHONDRITE_BRICK_SLAB = slabFactory(CHONDRITE_BRICKS, "chondrite_brick_slab");


        // Walls
        CHONDRITE_BRICK_WALL = wallFactory(CHONDRITE_BRICKS, "chondrite_brick_wall");

        // Stairs - still need class here as constructor in StairsBlock is protected
        CHONDRITE_BRICK_STAIRS = new StairMod("chondrite_brick_stairs", ModBlock.CHONDRITE_BRICKS);

        // Tile Blocks
        CHONDRITE_BLAST_FURNACE = new ChondriteBlastFurnaceBlock("chondrite_blast_furnace");
        RELIQUARY = new ReliquaryBlock("reliquary");


        // Register all the blocks
        event.getRegistry().registerAll(POLISHED_CHONDRITE, CHONDRITE_BRICKS, CHONDRITE_BRICK_SLAB, CHONDRITE_BRICK_STAIRS,
                CHONDRITE_BRICK_WALL, CHONDRITE_BLAST_FURNACE, RELIQUARY);


    }

    public static SlabBlock slabFactory(Block block, String registryName)
    {
        SlabBlock slabBlock = new SlabBlock(Block.Properties.from(block));
        slabBlock.setRegistryName(registryName);

        return slabBlock;
    }

    public static WallBlock wallFactory(Block block, String registryName)
    {
        WallBlock wallBlock = new WallBlock(Block.Properties.from(block));
        wallBlock.setRegistryName(registryName);
        return wallBlock;
    }



}
