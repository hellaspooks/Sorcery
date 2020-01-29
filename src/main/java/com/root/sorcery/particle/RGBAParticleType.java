package com.root.sorcery.particle;

import net.minecraft.particles.ParticleType;

public class RGBAParticleType extends ParticleType<RGBAParticleData>
{
    public RGBAParticleType()
    {
        super(false, RGBAParticleData.DESERIALIZER);
    }
}
