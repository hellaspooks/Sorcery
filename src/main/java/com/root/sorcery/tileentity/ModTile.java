package com.root.sorcery.tileentity;

import com.root.sorcery.block.ModBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;


public class ModTile
{


    public static void init(RegistryEvent.Register<TileEntityType<?>> event)
    {

        event.getRegistry().register(TileEntityType.Builder
                .create(ChondriteBlastFurnaceTileEntity::new, ModBlock.CHONDRITE_BLAST_FURNACE)
                .build(null)
                .setRegistryName("chondrite_blast_furnace"));

        event.getRegistry().register(TileEntityType.Builder
                .create(ReliquaryTileEntity::new, ModBlock.RELIQUARY)
                .build(null)
                .setRegistryName("reliquary"));


    }

}
