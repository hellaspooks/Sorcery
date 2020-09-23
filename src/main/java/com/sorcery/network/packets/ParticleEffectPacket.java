package com.sorcery.network.packets;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sorcery.particle.ParticleCollection;
import com.sorcery.particle.ParticleEffectContext;
import com.sorcery.particle.ParticleEffects;
import com.sorcery.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
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

    public ParticleEffectPacket(int effectType, IParticleData particle, Vector3d loc, Vector3d lookVec, int numParticles, double speed, double radius, int age)
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("eT", effectType);

        nbt.putBoolean("pC", false);

        nbt.putString("p", particle.getType().getRegistryName().toString());
        nbt.putString("pP", particle.getParameters());

        nbt.putDouble("lX", loc.x);
        nbt.putDouble("lY", loc.y);
        nbt.putDouble("lZ", loc.z);

        nbt.putDouble("vX", lookVec.x);
        nbt.putDouble("vY", lookVec.y);
        nbt.putDouble("vZ", lookVec.z);

        nbt.putInt("n", numParticles);

        nbt.putDouble("v", speed);
        nbt.putDouble("r", radius);
        nbt.putInt("t", age);

        this.pktNBT = nbt;
    }

    public ParticleEffectPacket(int effectType, int particleSet, Vector3d loc, Vector3d lookVec, int numParticles, double speed, double radius, int age)
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("eT", effectType);

        nbt.putBoolean("pC", true);
        nbt.putInt("pN", particleSet);

        nbt.putDouble("lX", loc.x);
        nbt.putDouble("lY", loc.y);
        nbt.putDouble("lZ", loc.z);

        nbt.putDouble("vX", lookVec.x);
        nbt.putDouble("vY", lookVec.y);
        nbt.putDouble("vZ", lookVec.z);

        nbt.putInt("n", numParticles);

        nbt.putDouble("v", speed);
        nbt.putDouble("r", radius);
        nbt.putInt("t", age);

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
                    ParticleCollection collection = new ParticleCollection();

                    if (!nbt.getBoolean("pC"))
                    {
                        IForgeRegistryEntry regParticle = GameRegistry.findRegistry(ParticleType.class).getValue(new ResourceLocation(nbt.getString("p")));
                        ParticleType particleType = (ParticleType) regParticle;
                        IParticleData particle = null;
                        try {
                            particle = particleType.getDeserializer().deserialize(particleType, new StringReader(nbt.getString("pP")));
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                        collection.add(100, particle);

                    } else {
                        collection = Particles.getParticleSet(nbt.getInt("pN"));
                    }


                    Vector3d loc = new Vector3d(nbt.getDouble("lX"), nbt.getDouble("lY"), nbt.getDouble("lZ"));
                    Vector3d lookVec = new Vector3d(nbt.getDouble("vX"), nbt.getDouble("vY"), nbt.getDouble("vZ"));

                    int num = nbt.getInt("n");

                    double speed = nbt.getDouble("v");
                    double radius = nbt.getDouble("r");
                    int age = nbt.getInt("t");

                    ParticleEffectContext context = new ParticleEffectContext(world, collection, loc, lookVec, num, speed, radius, age);

                    switch (message.pktNBT.getInt("eT"))
                    {
                        case 0:
                            ParticleEffects.risePoof(context);
                            break;
                        case 1:
                            ParticleEffects.ringHorizontal(context);
                            break;
                        case 2:
                            ParticleEffects.expandingSphere(context);
                            break;
                        case 3:
                            ParticleEffects.coneSpray(context);
                            break;
                        case 4:
                            ParticleEffects.sendTo(context);
                            break;
                        case 5:
                            ParticleEffects.smallFountain(context);
                            break;
                        case 6:
                            ParticleEffects.drawIn(context);
                            break;
                        case 7:
                            ParticleEffects.drawInFrom(context);
                            break;
                        case 8:
                            ParticleEffects.staticHorizontalRing(context);
                            break;

                    }
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
