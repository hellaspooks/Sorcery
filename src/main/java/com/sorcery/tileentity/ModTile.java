package com.sorcery.tileentity;

import com.sorcery.block.ModBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;



@ObjectHolder("sorcery")
public class ModTile
{

    @ObjectHolder("chiseled_monolith")
    public static TileEntityType<AbstractMonolithTile> CHISELED_MONOLITH_TILE;

    @ObjectHolder("solar_monolith")
    public static TileEntityType<SolarMonolithTile> SOLAR_MONOLITH_TILE;

    @ObjectHolder("lunar_monolith")
    public static TileEntityType<LunarMonolithTile> LUNAR_MONOLITH_TILE;

    @ObjectHolder("dark_monolith")
    public static TileEntityType<DarkMonolithTile> DARK_MONOLITH_TILE;

    @ObjectHolder("lapis_monolith")
    public static TileEntityType<LapisMonolithTile> LAPIS_MONOLITH_TILE;

    @ObjectHolder("pylon")
    public static TileEntityType<PylonTile> PYLON_TILE;


    public static void init(RegistryEvent.Register<TileEntityType<?>> event)
    {

        event.getRegistry().register(TileEntityType.Builder
                .create(ChiseledMonolithTile::new, ModBlock.MONOLITH_CHISELED.get())
                .build(null)
                .setRegistryName("chiseled_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(DarkMonolithTile::new, ModBlock.MONOLITH_DARK.get())
                .build(null)
                .setRegistryName("dark_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(SolarMonolithTile::new, ModBlock.MONOLITH_SOLAR.get())
                .build(null)
                .setRegistryName("solar_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(LunarMonolithTile::new, ModBlock.MONOLITH_LUNAR.get())
                .build(null)
                .setRegistryName("lunar_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(LapisMonolithTile::new, ModBlock.MONOLITH_LAPIS.get())
                .build(null)
                .setRegistryName("lapis_monolith"));

        event.getRegistry().register(TileEntityType.Builder
                .create(PylonTile::new, ModBlock.PYLON.get())
                .build(null)
                .setRegistryName("pylon"));

    }

}
