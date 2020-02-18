package com.root.sorcery.tileentity;

import com.root.sorcery.block.MonolithBlock;
import com.root.sorcery.particle.ParticleEffects;
import com.root.sorcery.particle.Particles;
import com.root.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class LunarMonolithTile extends ArcanaStorageTile implements ITickableTileEntity
{

    protected int arcanaPerRegen;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    private double cycleMultiplier = 0.5;

    private Map<Integer, Double> phaseMap = new HashMap();


    public LunarMonolithTile(){
        super(ModTile.LUNAR_MONOLITH_TILE);
        this.arcanaStorage.setMaxArcanaStored(1000);
        this.arcanaPerRegen = 10;
        this.phaseMap.put(0, 3d);
        this.phaseMap.put(1, 2d);
        this.phaseMap.put(2, 1d);
        this.phaseMap.put(3, 0.75d);
        this.phaseMap.put(4, 0.5d);
        this.phaseMap.put(5, 0.75d);
        this.phaseMap.put(6, 1d);
        this.phaseMap.put(7, 2d);
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
                this.receiveArcana((int)((double)this.arcanaPerRegen * this.cycleMultiplier));
            }

            // Activity setting
            if (worldTicks % 20 == 0)
            {
                // TODO: make sure monolith is above ground
                if (this.world.isDaytime())
                {
                    this.active = false;
                } else {
                    this.active = true;
                   int moonPhase = this.world.dimension.getMoonPhase(worldTicks);
                   this.cycleMultiplier = this.phaseMap.get(moonPhase);
                }
                MonolithBlock.setActivity(this.world, this.getBlockState(), this.pos, this.active);
            }
        } else {
            // Particles
            if (worldTicks % 5 == 0)
            {
                if (this.getBlockState().get(MonolithBlock.ACTIVE))
                {
                    Vec3d moonVec = Utils.getMoonVector(this.world);
                    ParticleEffects.drawIn(world, Particles.getLunarSpark(), this.arcanaPulseSource, moonVec, 10, 1, 1, 40);
                }
            }

        }
        super.tick();

    }
}
