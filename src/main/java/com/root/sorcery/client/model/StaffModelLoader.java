package com.root.sorcery.client.model;

import com.google.common.collect.ImmutableMap;
import com.root.sorcery.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

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
    public static final ResourceLocation INITIATE_MODEL_RL = new ResourceLocation(Constants.MODID, "item/i_staff");
    //public static final ModelResourceLocation INITIATE_MODEL_MRL = new ModelResourceLocation(INITIATE_MODEL_RL, "inventory");


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
        System.out.println("Accept model?");
        System.out.println(modelLocation.toString());
        boolean accepted = modelLocation.toString().equals("sorcery:models/item/initiate_staff");
        System.out.println(accepted);
        return accepted;
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception
    {
        return new StaffModel();
    }

    public class StaffModel implements IUnbakedModel
    {

        private IUnbakedModel baseModel;

        private ResourceLocation rodLocation = new ResourceLocation(Constants.MODID, "staves/jungle_rod");
        private ResourceLocation catalystLocation = new ResourceLocation(Constants.MODID, "staves/initiate_catalyst");
        private ResourceLocation fittingLocation = new ResourceLocation(Constants.MODID, "staves/initiate_gold_fittings");

        public StaffModel()
        {
            this.baseModel = ModelLoaderRegistry.getModelOrLogError(INITIATE_MODEL_RL, "model missing");
        }

        public StaffModel(ResourceLocation rodLocation, ResourceLocation catalystLocation, ResourceLocation fittingLocation)
        {
            this.rodLocation = rodLocation;
            this.catalystLocation = catalystLocation;
            this.fittingLocation = fittingLocation;
        }

        @Override
        public Collection<ResourceLocation> getDependencies()
        {
            return Collections.EMPTY_SET;
        }

        @Override
        public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors)
        {
            Set<ResourceLocation> deps = Collections.EMPTY_SET;

            deps.add(rodLocation);
            deps.add(catalystLocation);
            deps.add(fittingLocation);

            return deps;
        }

        @Override
        public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
        {

            IUnbakedModel retexBase = this.retexture(this.rodLocation, this.catalystLocation, this.fittingLocation);

            IBakedModel bakedBase = retexBase.bake(bakery, spriteGetter, sprite, format);
            return new BakedStaffModel(this, bakedBase, bakery, spriteGetter, sprite, format);
        }


        // In progress, not final
        public IUnbakedModel retexture(ResourceLocation rodLocation, ResourceLocation catalystLocation, ResourceLocation fittingLocation) {

            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

            builder.put("rod", rodLocation.toString());
            builder.put("catalyst", catalystLocation.toString());
            builder.put("fitting", fittingLocation.toString());

            return this.baseModel.retexture(builder.build());
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
        private IUnbakedModel parent;

        public BakedStaffModel(IUnbakedModel parent, IBakedModel def, ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
            this.parent = parent;
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
            BakedStaffModel bakedStaffModel = (BakedStaffModel) model;


            return model;
        }
    }
}
