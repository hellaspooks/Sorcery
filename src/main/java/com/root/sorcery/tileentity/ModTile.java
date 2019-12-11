package com.root.sorcery.tileentity;

import com.root.sorcery.block.ModBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;



@ObjectHolder("sorcery")
public class ModTile
{
    @ObjectHolder("wolfram_blast_furnace")
    public static TileEntityType<WolframBlastFurnaceTile> WOLFRAM_BLAST_FURNACE_TILE;

    @ObjectHolder("monolith")
    public static TileEntityType<MonolithTile> MONOLITH_TILE;

    @ObjectHolder("reliquary")
    public static TileEntityType<ReliquaryTile> RELIQUARY_TILE;

    @ObjectHolder("pylon")
    public static TileEntityType<PylonTile> PYLON_TILE;


    public static void init(RegistryEvent.Register<TileEntityType<?>> event)
    {

        event.getRegistry().register(TileEntityType.Builder
                .create(WolframBlastFurnaceTile::new, ModBlock.WOLFRAM_BLAST_FURNACE)
                .build(null)
                .setRegistryName("wolfram_blast_furnace"));

        event.getRegistry().register(TileEntityType.Builder
                .create(ReliquaryTile::new, ModBlock.RELIQUARY)
                .build(null)
                .setRegistryName("reliquary"));

        event.getRegistry().register(TileEntityType.Builder
                .create(MonolithTile::new, ModBlock.MONOLITH_BASIC)
                .build(null)
                .setRegistryName("monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(PylonTile::new, ModBlock.PYLON)
                .build(null)
                .setRegistryName("pylon"));
    }

}
