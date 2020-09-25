package com.sorcery.setup;

import com.sorcery.Constants;
import com.sorcery.particle.ModParticle;
import com.sorcery.particle.RGBAParticle;
import com.sorcery.particle.RGBLitParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Client-side registry events
@Mod.EventBusSubscriber(modid = Constants.MODID, value= Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistryEvents
{

    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event)
    {
        Minecraft mc = Minecraft.getInstance();

        mc.particles.registerFactory(ModParticle.SIMPLE_PUFF, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.RGBA_SPARK, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.ARCANA_ORB, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.ARCANA_SPARK_1, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.ARCANA_SPARK_3, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.SIMPLE_SPARK, RGBAParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.LIT_SPARK, RGBLitParticle.Factory::new);
        mc.particles.registerFactory(ModParticle.SKULL_SMOKE, RGBAParticle.Factory::new);
    }

}
