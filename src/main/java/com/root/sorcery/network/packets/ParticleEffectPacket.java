package com.root.sorcery.network.packets;

import com.root.sorcery.particle.ParticleEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class ParticleEffectPacket
{
    private CompoundNBT pktNBT;

    public ParticleEffectPacket(){}

    public ParticleEffectPacket(CompoundNBT nbt)
    {
        this.pktNBT = nbt;
    }

    public ParticleEffectPacket(int effectType, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius)
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("eT", effectType);
        nbt.putString("p", particle.getType().getRegistryName().toString());

        nbt.putDouble("lX", loc.x);
        nbt.putDouble("lY", loc.y);
        nbt.putDouble("lZ", loc.z);

        nbt.putDouble("vX", lookVec.x);
        nbt.putDouble("vY", lookVec.y);
        nbt.putDouble("vZ", lookVec.z);

        nbt.putInt("n", numParticles);

        nbt.putDouble("v", speed);
        nbt.putDouble("r", radius);

        this.pktNBT = nbt;
    }

    public static void encode(ParticleEffectPacket pkt, PacketBuffer buf)
    {
        buf.writeCompoundTag(pkt.pktNBT);
    }

    public static ParticleEffectPacket decode(PacketBuffer buf)
    {
        return new ParticleEffectPacket(buf.readCompoundTag());
    }

    public static class Handler
    {
        public static void handle(final ParticleEffectPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getDirection().getReceptionSide().isClient()) {
                    CompoundNBT nbt = message.pktNBT;
                    World world = Minecraft.getInstance().world;
                    IForgeRegistryEntry particle = GameRegistry.findRegistry(ParticleType.class).getValue(new ResourceLocation(nbt.getString("p")));

                    Vec3d loc = new Vec3d(nbt.getDouble("lX"), nbt.getDouble("lY"), nbt.getDouble("lZ"));
                    Vec3d lookVec = new Vec3d(nbt.getDouble("vX"), nbt.getDouble("vY"), nbt.getDouble("vZ"));

                    int num = nbt.getInt("n");

                    double speed = nbt.getDouble("v");
                    double radius = nbt.getDouble("r");

                    switch (message.pktNBT.getInt("eT"))
                    {
                        case 0:
                            ParticleEffects.risePoof(world, ((BasicParticleType) particle), loc, lookVec, num, speed, radius);
                            break;
                        case 1:
                            ParticleEffects.ringHorizontal(world, ((BasicParticleType) particle), loc, lookVec, num, speed, radius);
                            break;
                        case 2:
                            ParticleEffects.expandingSphere(world, ((BasicParticleType) particle), loc, lookVec, num, speed, radius);
                            break;
                        case 3:
                            ParticleEffects.coneSpray(world, ((BasicParticleType) particle), loc, lookVec, num, speed, radius);
                            break;
                    }
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
