package com.root.sorcery.tileentity;

import com.root.sorcery.block.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;


public class ModTile
{
    @ObjectHolder("sorcery:chondrite_blast_furnace")
    public static TileEntityType<ChondriteBlastFurnaceTile> CHONDRITE_BLAST_FURNACE_TILE;

    @ObjectHolder("sorcery:monolith")
    public static TileEntityType<MonolithTile> MONOLITH_TILE;

    @ObjectHolder("sorcery:reliquary")
    public static TileEntityType<ReliquaryTile> RELIQUARY_TILE;

    public static void init(RegistryEvent.Register<TileEntityType<?>> event)
    {

        event.getRegistry().register(TileEntityType.Builder
                .create(ChondriteBlastFurnaceTile::new, ModBlock.CHONDRITE_BLAST_FURNACE)
                .build(null)
                .setRegistryName("chondrite_blast_furnace"));

        event.getRegistry().register(TileEntityType.Builder
                .create(ReliquaryTile::new, ModBlock.RELIQUARY)
                .build(null)
                .setRegistryName("reliquary"));

    }

}
