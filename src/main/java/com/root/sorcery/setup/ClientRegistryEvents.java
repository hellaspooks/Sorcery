package com.root.sorcery.setup;

import com.root.sorcery.Constants;
import com.root.sorcery.client.model.StaffModelLoader;
import com.root.sorcery.particle.ModParticle;
import com.root.sorcery.particle.RGBAParticle;
import com.root.sorcery.particle.RGBLitParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

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

    @SubscribeEvent
    public void onModelRegistryEvent(ModelRegistryEvent event)
    {
        // Registers Custom Model Loader
        ModelLoaderRegistry.registerLoader(new ResourceLocation(Constants.MODID, "sorcerous_staff"), new StaffModelLoader());
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event)
    {
        try {
            // Loads model from registered model loader
            IUnbakedModel model = ModelLoader.instance().getModelOrMissing(new ResourceLocation(Constants.MODID, "item/sorcerous_staff"));

            IBakedModel bakedModel = model.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(), SimpleModelTransform.IDENTITY, new ResourceLocation(Constants.MODID, "item/sorcerous_staff"));

            event.getModelRegistry().put(new ModelResourceLocation("sorcery:sorcerous_staff", "inventory"), bakedModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void onTextureStitchEventPre(TextureStitchEvent.Pre event)
    {
        // texture dependencies not working, so adding needed textures here

        ArrayList<String> baseTypes = new ArrayList<>();
        baseTypes.add("initiate");
        baseTypes.add("apprentice");
        baseTypes.add("magician");
        baseTypes.add("archmage");

        ArrayList<String> fittingTypes = new ArrayList<>();
        fittingTypes.add("iron");
        fittingTypes.add("gold");
        fittingTypes.add("wolfram");
        fittingTypes.add("mythril");

        ArrayList<String> rodTypes = new ArrayList<>();
        rodTypes.add("acacia");
        rodTypes.add("birch");
        rodTypes.add("dark_oak");
        rodTypes.add("jungle");
        rodTypes.add("oak");
        rodTypes.add("spruce");

        for (String base : baseTypes)
        {
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/" + base + "_catalyst"));

            for (String fitting : fittingTypes)
            {
                event.addSprite(new ResourceLocation(Constants.MODID, "staves/" + base + "_" + fitting + "_fittings"));
            }
        }

        for (String rod : rodTypes)
        {
            event.addSprite(new ResourceLocation(Constants.MODID, "staves/" + rod + "_rod"));
        }
    }


}
