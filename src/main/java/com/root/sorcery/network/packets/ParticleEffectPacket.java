package com.root.sorcery.network.packets;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.root.sorcery.particle.ParticleEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
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

    public ParticleEffectPacket(int effectType, IParticleData particle, Vec3d loc, Vec3d lookVec, int numParticles, double speed, double radius, int age)
    {
        CompoundNBT nbt = new CompoundNBT();

        nbt.putInt("eT", effectType);

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
                    IForgeRegistryEntry regParticle = GameRegistry.findRegistry(ParticleType.class).getValue(new ResourceLocation(nbt.getString("p")));
                    ParticleType particleType = (ParticleType) regParticle;
                    IParticleData particle = null;
                    try {
                        particle = particleType.getDeserializer().deserialize(particleType, new StringReader(nbt.getString("pP")));
                    } catch (CommandSyntaxException e) {
                        e.printStackTrace();
                    }


                    Vec3d loc = new Vec3d(nbt.getDouble("lX"), nbt.getDouble("lY"), nbt.getDouble("lZ"));
                    Vec3d lookVec = new Vec3d(nbt.getDouble("vX"), nbt.getDouble("vY"), nbt.getDouble("vZ"));

                    int num = nbt.getInt("n");

                    double speed = nbt.getDouble("v");
                    double radius = nbt.getDouble("r");
                    int age = nbt.getInt("t");

                    switch (message.pktNBT.getInt("eT"))
                    {
                        case 0:
                            ParticleEffects.risePoof(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 1:
                            ParticleEffects.ringHorizontal(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 2:
                            ParticleEffects.expandingSphere(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 3:
                            ParticleEffects.coneSpray(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 4:
                            ParticleEffects.sendTo(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 5:
                            ParticleEffects.smallFountain(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 6:
                            ParticleEffects.drawIn(world, particle, loc, lookVec, num, speed, radius, age);
                            break;
                        case 7:
                            System.out.println("drawing in from");
                            ParticleEffects.drawInFrom(world, particle, loc, lookVec, num, speed, radius, age);
                            break;

                    }
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
