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

    @ObjectHolder("basic_monolith")
    public static TileEntityType<AbstractMonolithTile> BASIC_MONOLITH_TILE;

    @ObjectHolder("solar_monolith")
    public static TileEntityType<SolarMonolithTile> SOLAR_MONOLITH_TILE;

    @ObjectHolder("lunar_monolith")
    public static TileEntityType<LunarMonolithTile> LUNAR_MONOLITH_TILE;

    @ObjectHolder("dark_monolith")
    public static TileEntityType<DarkMonolithTile> DARK_MONOLITH_TILE;

    @ObjectHolder("lapis_monolith")
    public static TileEntityType<LapisMonolithTile> LAPIS_MONOLITH_TILE;

    @ObjectHolder("reliquary")
    public static TileEntityType<ReliquaryTile> RELIQUARY_TILE;

    @ObjectHolder("pylon")
    public static TileEntityType<PylonTile> PYLON_TILE;

    @ObjectHolder("staff_lathe")
    public static TileEntityType<StaffLatheTile> STAFF_LATHE_TILE;


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
                .create(BasicMonolithTile::new, ModBlock.MONOLITH_BASIC)
                .build(null)
                .setRegistryName("basic_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(DarkMonolithTile::new, ModBlock.MONOLITH_DARK)
                .build(null)
                .setRegistryName("dark_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(SolarMonolithTile::new, ModBlock.MONOLITH_SOLAR)
                .build(null)
                .setRegistryName("solar_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(LunarMonolithTile::new, ModBlock.MONOLITH_LUNAR)
                .build(null)
                .setRegistryName("lunar_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(LapisMonolithTile::new, ModBlock.MONOLITH_LAPIS)
                .build(null)
                .setRegistryName("lapis_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(PylonTile::new, ModBlock.PYLON)
                .build(null)
                .setRegistryName("pylon"));

        event.getRegistry().register(TileEntityType.Builder
                .create(StaffLatheTile::new, ModBlock.STAFF_LATHE)
                .build(null)
                .setRegistryName("staff_lathe"));

    }

}
