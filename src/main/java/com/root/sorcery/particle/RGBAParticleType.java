package com.root.sorcery.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;
import java.util.Locale;

public class RGBAParticleType extends ParticleType<RGBAParticleType> implements IParticleData
{
    public final float r;
    public final float g;
    public final float b;
    public final float a;

    public RGBAParticleType()
    {
        super(false, RGBAParticleType.DESERIALIZER);
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;

    }

    public RGBAParticleType(float r, float g, float b, float a)
    {

        super(false, RGBAParticleType.DESERIALIZER);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public ParticleType<RGBAParticleType> getType()
    {
        return ModParticle.RGBA_SPARK;
    }

    @Override
    public void write(PacketBuffer buf)
    {
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);

    }

    @Override
    public String getParameters()
    {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %s",
                this.getType().getRegistryName(), this.r, this.g, this.b, this.a);
    }

    public static final IDeserializer<RGBAParticleType> DESERIALIZER = new IDeserializer<RGBAParticleType>() {
        @Nonnull
        @Override
        public RGBAParticleType deserialize(@Nonnull ParticleType<RGBAParticleType> type, @Nonnull StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float a = reader.readFloat();
            return new RGBAParticleType(r, g, b, a);
        }

        @Override
        public RGBAParticleType read(@Nonnull ParticleType<RGBAParticleType> type, PacketBuffer buf) {
            return new RGBAParticleType(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
    };
}
