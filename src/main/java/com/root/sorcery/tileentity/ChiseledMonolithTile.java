package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;

public class ChiseledMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen = 2;
    protected int ticksPerRegen = 2;

    protected boolean active = true;


    public ChiseledMonolithTile()
    {
        super(ModTile.CHISELED_MONOLITH_TILE, 1000);
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
            MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
        }
        super.tick();
    }

}
