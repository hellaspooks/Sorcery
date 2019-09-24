package com.root.sorcery.block;

import com.root.sorcery.tileentity.ChondriteBlastFurnaceTileEntity;
import com.root.sorcery.tileentity.ReliquaryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Collections;

import static com.root.sorcery.setup.ModSetup.sorcery;


public class ModBlock extends Block
{
    public ModBlock(Material material, Float hardness, Float resistance, SoundType sound, String registryName)
    {
        super(Properties.create(material).sound(sound).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(registryName);
        Registry.register(Registry.BLOCK, registryName, this);


    }

    @ObjectHolder("sorcery:chondriteblastfurnace")
    public static TileEntityType<ReliquaryTileEntity> TILE_RELIQUARY;
    public static TileEntityType<ChondriteBlastFurnaceTileEntity> TILE_CHONDRITE_BLAST_FURNACE;

    public static ModBlock  polished_chondrite;
    public static BlockItem polished_chondrite_block;
    public static ModBlock  chondrite_bricks;
    public static BlockItem chondrite_bricks_block;
    public static Block     reliquary;
    public static BlockItem reliquary_block;
    public static ChondriteBlastFurnaceBlock chondrite_blast_furnace;
    public static BlockItem chondrite_blast_furnace_block;

    public static void init()
    {

        reliquary = new ReliquaryBlock(Material.ROCK, 3.0F, 6.0F, SoundType.STONE, "reliquary");
        reliquary_block = (BlockItem) new BlockItem(reliquary, new Item.Properties().group(sorcery)).setRegistryName("reliquary_block");
        Registry.register(Registry.ITEM, "reliquary_block", reliquary_block);

        polished_chondrite = new ModBlock(Material.ROCK, 3.0F, 5.0F, SoundType.STONE, "polished_chondrite");
        polished_chondrite_block = (BlockItem) new BlockItem(polished_chondrite, new Item.Properties().group(sorcery)).setRegistryName("polished_chondrite_block");
        Registry.register(Registry.ITEM, "polished_chondrite_block", polished_chondrite_block);

        chondrite_bricks = new ModBlock(Material.ROCK, 3.0F, 5.0F, SoundType.STONE, "chondrite_bricks");
        chondrite_bricks_block = (BlockItem) new BlockItem(chondrite_bricks, new Item.Properties().group(sorcery)).setRegistryName("chondrite_bricks_block");
        Registry.register(Registry.ITEM, "chondrite_bricks_block", chondrite_bricks_block);

        chondrite_blast_furnace = new ChondriteBlastFurnaceBlock();
        chondrite_blast_furnace_block = (BlockItem) new BlockItem(chondrite_blast_furnace, new Item.Properties().group(sorcery)).setRegistryName("chondrite_blast_furnace_block");
        Registry.register(Registry.ITEM, "chondrite_blast_furnace_block", chondrite_blast_furnace_block);

        TILE_RELIQUARY = new TileEntityType<>(ReliquaryTileEntity::new, Collections.singleton(reliquary), null);
        TILE_CHONDRITE_BLAST_FURNACE = new TileEntityType<>(ChondriteBlastFurnaceTileEntity::new, Collections.singleton(chondrite_blast_furnace), null);
    }
}
