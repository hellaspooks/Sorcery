package com.root.sorcery.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Locale;

public class RGBAParticleData implements IParticleData
{
    public final float r;
    public final float g;
    public final float b;
    public final float a;
    public final int t;
    public ParticleType<RGBAParticleData> particleType;

    public RGBAParticleData()
    {
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
        this.t = 40;
        this.particleType = ModParticle.RGBA_SPARK;

    }

    public RGBAParticleData(float r, float g, float b, float a)
    {

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = 40;
        this.particleType = ModParticle.RGBA_SPARK;
    }

    public RGBAParticleData(float r, float g, float b, float a, int t)
    {

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.particleType = ModParticle.RGBA_SPARK;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = 40;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.particleType = particleType;
    }

    @Override
    public ParticleType<RGBAParticleData> getType()
    {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buf)
    {
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);
        buf.writeInt(t);

    }

    @Override
    public String getParameters()
    {
        String params = String.format(Locale.ROOT, "%s %f %f %f %f %d", this.getType().getRegistryName(), this.r, this.g, this.b, this.a, this.t);
        System.out.println(params);
        return params;
    }

    public static final IDeserializer<RGBAParticleData> DESERIALIZER = new IDeserializer<RGBAParticleData>() {
        @Nonnull
        @Override
        public RGBAParticleData deserialize(@Nonnull ParticleType<RGBAParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException
        {
            String regName = reader.readStringUntil(' ');
            IForgeRegistryEntry regParticle = GameRegistry.findRegistry(ParticleType.class).getValue(new ResourceLocation(regName));
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float a = reader.readFloat();
            reader.expect(' ');
            int t = reader.readInt();

            return new RGBAParticleData((ParticleType)regParticle, r, g, b, a, t);
        }

        @Override
        public RGBAParticleData read(@Nonnull ParticleType<RGBAParticleData> type, PacketBuffer buf) {
            return new RGBAParticleData(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt());
        }
    };
}
