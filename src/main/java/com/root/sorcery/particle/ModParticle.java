package com.root.sorcery.particle;

import com.root.sorcery.Constants;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

/**
 * To add a new particle, do the following:
 * 1) Add a new BasicParticleType below.
 * 2) Add a [name].json file in assets/sorcery/particles, matching the name in the ObjectHolder
 * 3) Ensure textures referenced in that file are in assets/sorcery/textures/particle
 * 4) Register a factory for that particle in ParticleFactoryRegisterEvent event
 */
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticle
{

    @ObjectHolder("sorcery:puff")
    public static BasicParticleType SIMPLE_PUFF;

    @ObjectHolder("sorcery:spark1")
    public static RGBAParticleType RGBA_SPARK;

    @ObjectHolder("sorcery:arcana_orb")
    public static RGBAParticleType ARCANA_ORB;

    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event)
    {
        SIMPLE_PUFF = basicParticleFactory("sorcery:puff", event);
        registerParticle(new RGBAParticleType(), "sorcery:spark1", event);
        registerParticle(new RGBAParticleType(), "sorcery:arcana_orb", event);
    }

    public static void registerParticle(ParticleType<?> particleType, String regName, RegistryEvent.Register<ParticleType<?>> event)
    {
        particleType.setRegistryName(regName);
        event.getRegistry().register(particleType);
    }

    public static BasicParticleType basicParticleFactory(String regName, RegistryEvent.Register<ParticleType<?>> event)
    {
        BasicParticleType newParticle = new BasicParticleType(true);
        newParticle.setRegistryName(regName);
        event.getRegistry().register(newParticle);
        return newParticle;
    }
}
