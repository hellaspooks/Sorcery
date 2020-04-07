package com.root.sorcery.tileentity;

import com.root.sorcery.Constants;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.Particles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

public class ConjuredLightTile extends TileEntity implements ITickableTileEntity
{
    public ConjuredLightTile()
    {
        super(ModTile.CONJURED_LIGHT_TILE);
    }

    @Override
    public void tick()
    {
        if (this.world.isRemote())
        {
            if (this.world.getGameTime() % 20 == 0)
            {
                // refresh particle effect
                Vec3d pos = new Vec3d(this.pos).add(0.5, 0.5, 0.5);
                // center orb
                this.world.addParticle(Particles.getColoredParticle(Constants.ARCANA_PURPLE_MAIN, ModParticle.ARCANA_ORB, 0.7f), pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);

            }
        }

    }
}
