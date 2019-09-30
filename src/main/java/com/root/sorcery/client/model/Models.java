package com.root.sorcery.client.model;

import com.root.sorcery.client.LichMod;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.Random;

import static com.root.sorcery.Constants.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Models
{
    private static final Random RAND          = new Random();
    private static final Quat4f LICH_ROTATION = new Quat4f(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event)
    {
        IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation(MODID + ":block/reliquary"));

        TRSRTransformation modelTRSRT = ((TRSRTransformation) model.getDefaultState()).compose(new TRSRTransformation(
                new Vector3f(0, 1, 0),
                LichMod.isLichModActivated() ? LICH_ROTATION : null,
                null,
                null));

        Direction.Plane.HORIZONTAL.iterator().forEachRemaining(direction ->
        {
            IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(),
                    new BasicState(modelTRSRT.compose(TRSRTransformation.from(direction)), false),
                    DefaultVertexFormats.BLOCK);
            event.getModelRegistry().put(new ModelResourceLocation(MODID + ":reliquary",
                    "facing=" + direction.getName2()), bakedModel);
        });
    }
}
