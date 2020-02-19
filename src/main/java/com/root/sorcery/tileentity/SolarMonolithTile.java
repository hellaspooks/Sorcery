package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.particle.Particles;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

public class SolarMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    public SolarMonolithTile(){
        super(ModTile.SOLAR_MONOLITH_TILE, 1000);
        this.arcanaStorage.extractArcana(1000, false);
        this.arcanaPerRegen = 4;
    }

    @Override
    public void tick()
    {
        long worldTicks = this.getOffsetWorldTicks();

        if (!this.world.isRemote())
        {
            // Arcana generation
            if (worldTicks % ticksPerRegen == 0 && this.active && !this.interference)
            {
                this.receiveArcana(arcanaPerRegen);
            }
            // Activity setting
            if (worldTicks % 20 == 0)
            {
                if (this.world.dimension.hasSkyLight())
                {
                    if (this.world.getLightFor(LightType.SKY, this.pos) >= 14 && this.world.isDaytime())
                    {
                        this.active = true;
                    } else {
                        this.active = false;
                    }
                } else {
                    this.active = false;
                }
                MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
            }
        } else {
            // Particles
            if (worldTicks % 5 == 0)
            {
                if (this.getBlockState().get(MonolithBlock.ACTIVE))
                {
                    Vec3d sunVec = Utils.getSunVector(this.world);
                    ParticleEffects.drawIn(world, Particles.getSolarSpark(), this.arcanaPulseSource, sunVec, 10, 1, 1, 40);
                }
            }
        }
        super.tick();
    }
}
