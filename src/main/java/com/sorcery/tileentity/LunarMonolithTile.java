package com.sorcery.tileentity;

import com.sorcery.block.MonolithBlock;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import com.sorcery.utils.Utils;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;
import java.util.Map;

public class LunarMonolithTile extends AbstractMonolithTile implements ITickableTileEntity
{

    protected int arcanaPerRegen;
    protected int ticksPerRegen = 2;

    protected boolean active = false;

    private double cycleMultiplier = 0.5;

    private Map<Integer, Double> phaseMap = new HashMap();


    public LunarMonolithTile(){
        super(ModTile.LUNAR_MONOLITH_TILE, 1000);
        this.arcanaStorage.extractArcana(1000, false);
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
        long worldTicks = this.getOffsetWorldTicks();

        if (!this.world.isRemote())
        {
            // Arcana generation
            if (worldTicks % ticksPerRegen == 0 && this.active && !this.interference)
            {
                this.receiveArcana((int)((double)this.arcanaPerRegen * this.cycleMultiplier));
            }

            // Activity setting
            if (worldTicks % 20 == 0)
            {
                if (this.world.isDaytime())
                {
                    this.active = false;
                } else {
                    this.active = true;

                    int moonPhase = this.world.func_230315_m_().func_236035_c_(worldTicks);
                    this.cycleMultiplier = this.phaseMap.get(moonPhase);
                }
                if (this.interference)
                {
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
                    Vector3d moonVec = Utils.getMoonVector(this.world);
                    ParticleEffects.drawIn(new ParticleEffectContext(world, Particles.getLunarSparks(), this.arcanaPulseSource, moonVec, 10, 1, 1, 40));
                }
            }

        }
        super.tick();

    }
}
