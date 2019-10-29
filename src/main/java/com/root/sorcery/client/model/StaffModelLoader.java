package com.root.sorcery.client.model;

import com.root.sorcery.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class StaffModelLoader implements ICustomModelLoader
{
    public static final ModelResourceLocation INITIATE_MODEL = new ModelResourceLocation(new ResourceLocation(Constants.MODID, "i_staff"), "inventory");

    public StaffModelLoader()
    {
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation)
    {
        boolean accepted = modelLocation.toString().equals("sorcery:models/item/initiate_staff");
        return accepted;
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception
    {
        return new StaffModel();
    }

    public class StaffModel implements IUnbakedModel
    {

        @Override
        public Collection<ResourceLocation> getDependencies()
        {
            return Collections.EMPTY_SET;
        }

        @Override
        public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors)
        {
            Set<ResourceLocation> deps = Collections.EMPTY_SET;

            // Add all textures this model might depend on here.
            deps.add(new ResourceLocation(Constants.MODID, "staves/acacia_rod"));
            deps.add(new ResourceLocation(Constants.MODID, "staves/intiate_catalyst"));
            deps.add(new ResourceLocation(Constants.MODID, "staves/initiate_gold_fittings"));

            return deps;
        }

        @Override
        public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
        {
            System.out.println("IN BAKE MODEL BAKE");
            IBakedModel baseModel = bakery.getUnbakedModel(INITIATE_MODEL).bake(bakery, spriteGetter, sprite, format);
            return new BakedStaffModel(baseModel, bakery, spriteGetter, sprite, format);
        }

    }

    public class BakedStaffModel implements IBakedModel
    {
        private HashMap<Integer, IBakedModel> cache = new HashMap<>();
        private ModelBakery bakery;
        private Function<ResourceLocation, TextureAtlasSprite> spriteGetter;
        private ISprite sprite;
        private VertexFormat format;
        private IBakedModel def;
        private ItemOverrideList overrides = new StaffModelOverrideList();

        public BakedStaffModel(IBakedModel def, ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
            this.def = def;
            this.bakery = bakery;
            this.spriteGetter = spriteGetter;
            this.sprite = sprite;
            this.format = format;
        }

        @SuppressWarnings("deprecation")
        @Override
        public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
            return def.getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion() {
            return def.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return def.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer() {
            return def.isBuiltInRenderer();
        }

        @SuppressWarnings("deprecation")
        @Override
        public TextureAtlasSprite getParticleTexture() {
            return def.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides() {
            return overrides;
        }
    }



    public class StaffModelOverrideList extends ItemOverrideList
    {

        public StaffModelOverrideList()
        {
            super();
        }

        @Override
        public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn)
        {
            return model;
        }
    }
}
