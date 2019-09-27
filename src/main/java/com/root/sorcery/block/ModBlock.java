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
    public static Block polished_chondrite;

    @ObjectHolder("sorcery:chondrite_bricks")
    public static Block chondrite_bricks;

    // Stairs
    @ObjectHolder("sorcery:chondrite_brick_stairs")
    public static StairsBlock chondrite_brick_stairs;

    // Slabs
    @ObjectHolder("sorcery:chondrite_brick_slab")
    public static SlabBlock chondrite_brick_slab;

    // Walls
    @ObjectHolder("sorcery:chondrite_brick_wall")
    public static WallBlock chondrite_brick_wall;

    // Tile Blocks
    @ObjectHolder("sorcery:reliquary")
    public static Block reliquary;

    @ObjectHolder("sorcery:reliquary")
    public static TileEntityType<ReliquaryTileEntity> reliquary_tile;

    @ObjectHolder("sorcery:chondrite_blast_furnace")
    public static Block chondrite_blast_furnace;

    @ObjectHolder("sorcery:chondrite_blast_furnace")
    public static TileEntityType<ChondriteBlastFurnaceTileEntity> chondrite_blast_furnace_tile;


    public static void init(RegistryEvent.Register<Block> event)
    {
        // Simple Blocks
        polished_chondrite = new BlockMod("polished_chondrite", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);
        chondrite_bricks = new BlockMod("chondrite_bricks", Material.ROCK, 3.0F, 5.0F, SoundType.STONE);

        // Slabs
        chondrite_brick_slab = new SlabMod("chondrite_brick_slab", ModBlock.chondrite_bricks);

        // Stairs
        chondrite_brick_stairs = new StairMod("chondrite_brick_stairs", ModBlock.chondrite_bricks);

        // Walls
        chondrite_brick_wall = new WallMod("chondrite_brick_wall", ModBlock.chondrite_bricks);


        // Tile Blocks
        chondrite_blast_furnace = new ChondriteBlastFurnaceBlock("chondrite_blast_furnace");
        reliquary = new ReliquaryBlock("reliquary");


        // Register all the blocks
        event.getRegistry().registerAll(polished_chondrite, chondrite_bricks, chondrite_brick_slab, chondrite_brick_stairs,
                chondrite_brick_wall, chondrite_blast_furnace, reliquary);


    }

}
