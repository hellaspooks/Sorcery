package com.root.sorcery.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = false;


    public AbstractMonolithTile(TileEntityType variantIn, int maxArcana)
    {
        super(variantIn);
        this.arcanaStorage.setMaxArcanaStored(maxArcana);
        this.arcanaPulseOffset = new Vec3d(0.5, 2, 0.5);
    }

    @Override
    public void tick()
    {
        super.tick();
    }

}
