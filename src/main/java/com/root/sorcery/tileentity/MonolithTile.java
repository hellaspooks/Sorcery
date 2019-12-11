package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;

public class MonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    private static int arcanaPerRegen = 2;
    private static int ticksPerRegen = 2;

    private boolean active = true;


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

        if (worldTicks % ticksPerRegen == 0 && this.active)
        {
            this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
        }

        if (worldTicks % 20 == 0)
        {
            MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, active);
        }
        super.tick();
    }

}
