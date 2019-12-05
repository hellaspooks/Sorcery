package com.root.sorcery.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;

public class MonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    private static int arcanaPerRegen = 2;
    private static int ticksPerRegen = 2;


    public MonolithTile()
    {
        super(ModTile.MONOLITH_TILE);
        this.arcanaStorage.setMaxArcanaStored(1000);
        this.drainAll = true;
        this.arcanaPulseOffset = new Vec3d(0.5, 2, 0.5);
    }

    @Override
    public void tick()
    {
        long worldTicks = world.getWorld().getGameTime();

        if (worldTicks % ticksPerRegen == 0)
        {
            this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
        }
        super.tick();
    }

}
