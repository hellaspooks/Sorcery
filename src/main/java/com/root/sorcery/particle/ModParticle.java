package com.root.sorcery.particle;

import com.root.sorcery.Constants;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticle
{

    @ObjectHolder("sorcery:testparticle")
    public static BasicParticleType TESTPARTICLE;

    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event)
    {

        TESTPARTICLE = basicParticleFactory("sorcery:testparticle", event);

    }

    public static BasicParticleType basicParticleFactory(String regName, RegistryEvent.Register<ParticleType<?>> event)
    {
        BasicParticleType newParticle = new BasicParticleType(true);
        newParticle.setRegistryName(regName);
        event.getRegistry().register(newParticle);
        return newParticle;
    }
}
