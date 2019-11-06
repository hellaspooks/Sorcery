package com.root.sorcery.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;

public class MonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    private static int arcanaPerRegen = 2;
    private static int ticksPerRegen = 2;

    public MonolithTile()
    {
        super(ModTile.MONOLITH_TILE);
        this.transferRange = 8;
        this.arcanaStorage.setMaxArcanaStored(1000);
        this.drainAll = true;
    }

    @Override
    public void tick()
    {
        if (world.isRemote)
        {
            return;
        }

        long worldTicks = world.getWorld().getGameTime();

        if (worldTicks % ticksPerRegen == 0)
        {
            this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
        }
        super.tick();
    }
}
