package com.root.sorcery.tileentity;

import com.root.sorcery.arcana.IArcanaStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MonolithTile extends TileEntity
{
    @CapabilityInject(IArcanaStorage.class)
    static Capability<IArcanaStorage> ARCANA_STORAGE_CAP = null;

    public MonolithTile()
    {
        super(ModTile.MONOLITH_TILE);
    }
}
