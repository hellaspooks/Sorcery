package com.root.sorcery.client.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.vecmath.Vector3f;

import static com.root.sorcery.Constants.MODID;
import static net.minecraft.util.Direction.Plane.HORIZONTAL;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Models
{
    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event)
    {
        IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(MODID + ":block/reliquary" +
                ".obj")).process(ImmutableMap.of("flip-v", "true"));

        TRSRTransformation modelTRSRT = ((TRSRTransformation) model.getDefaultState()).compose(new TRSRTransformation(
                null,
                null,
                new Vector3f(0.0625F, 0.0625F, 0.0625F),
                null));

        if (model instanceof OBJModel)
        {
            HORIZONTAL.iterator().forEachRemaining(direction ->
            {
                IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
                        new BasicState(modelTRSRT.compose(TRSRTransformation.from(direction)), false),
                        DefaultVertexFormats.BLOCK);
                event.getModelRegistry().put(new ModelResourceLocation(MODID + ":reliquary",
                        "facing=" + direction.getName2()), bakedModel);
            });
        }
    }

    @SubscribeEvent
    public static void onPreTextureStitch(TextureStitchEvent.Pre event)
    {
        event.addSprite(ResourceLocation.tryCreate(MODID + ":blocks/reliquary"));
    }
}
