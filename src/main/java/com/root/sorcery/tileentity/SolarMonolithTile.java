package com.root.sorcery.tileentity;

import com.root.sorcery.block.BasicMonolithBlock;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

public class SolarMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    public SolarMonolithTile(){
        super(ModTile.SOLAR_MONOLITH_TILE, 1000);
        this.arcanaPerRegen = 4;
    }

    @Override
    public void tick()
    {
        long worldTicks = world.getWorld().getGameTime();

        if (!this.world.isRemote())
        {
            // Arcana generation
            if (worldTicks % ticksPerRegen == 0 && this.active)
            {
                this.arcanaStorage.receiveArcana(arcanaPerRegen, false);
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
                BasicMonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
            }
        } else {
            // Particles
            if (worldTicks % 10 == 0)
            {
                if (this.getBlockState().get(BasicMonolithBlock.ACTIVE))
                {
                    Vec3d sunVec = Utils.getSunVector(this.world);
                    ParticleEffects.drawIn(world, ParticleEffects.getSolarSpark(), this.arcanaPulseSource, sunVec, 20, 1, 1);
                }
            }
        }
        super.tick();
    }
}
