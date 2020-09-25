package com.sorcery.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

public class RGBAParticleType extends ParticleType<RGBAParticleData>
{
    public RGBAParticleType()
    {
        super(false, RGBAParticleData.DESERIALIZER);
    }

    @Override
    public Codec<RGBAParticleData> func_230522_e_()
    {
        return null;
    }
}
