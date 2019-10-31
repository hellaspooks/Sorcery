package com.root.sorcery.client.model;

import com.google.common.collect.ImmutableMap;
import com.root.sorcery.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
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
        return modelLocation.toString().equals("sorcery:models/item/sorcerous_staff");
    }

    @Override
    public IUnbakedModel loadModel(ResourceLocation modelLocation) throws Exception
    {
        return new StaffModel();
    }

    public class StaffModel implements IUnbakedModel
    {

        private IUnbakedModel initiateModel;
        private IUnbakedModel apprenticeModel;
        private IUnbakedModel magicianModel;
        private IUnbakedModel archmageModel;

        public final ResourceLocation initiateModelRL = new ResourceLocation(Constants.MODID, "item/initiate_staff_model");
        public final ResourceLocation apprenticeModelRL = new ResourceLocation(Constants.MODID, "item/apprentice_staff_model");
        public final ResourceLocation magicianModelRL = new ResourceLocation(Constants.MODID, "item/magician_staff_model");
        public final ResourceLocation archmageModelRL = new ResourceLocation(Constants.MODID, "item/archmage_staff_model");


        private String defaultRod = "oak_rod";
        private String defaultFitting = "_iron_fittings";
        private String defaultCatalyst = "_catalyst";

        public StaffModel()
        {
            this.initiateModel = ModelLoaderRegistry.getModelOrLogError(initiateModelRL, "model missing");
            this.apprenticeModel = ModelLoaderRegistry.getModelOrLogError(apprenticeModelRL, "model missing");
            this.magicianModel = ModelLoaderRegistry.getModelOrLogError(magicianModelRL, "model missing");
            this.archmageModel = ModelLoaderRegistry.getModelOrLogError(archmageModelRL, "model missing");
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
            return deps;
        }

        @Override
        public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
        {
            IUnbakedModel retexBase = this.retexture(null, null, null, null);

            IBakedModel bakedBase = retexBase.bake(bakery, spriteGetter, sprite, format);
            return new BakedStaffModel(this, bakedBase, bakery, spriteGetter, sprite, format);
        }


        public IUnbakedModel retexture(@Nullable String staffType, @Nullable ResourceLocation newRod, @Nullable ResourceLocation newCatalyst, @Nullable ResourceLocation newFitting)
        {
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

            if (staffType == null)
            {
                staffType = "initiate";
            }

            if (newRod != null)
            {
                builder.put("rod", newRod.toString());
            } else {
                builder.put("rod", Constants.MODID + ":staves/" + defaultRod);
            }

            if ( newCatalyst != null)
            {
                builder.put("catalyst", newCatalyst.toString());
            } else {
                builder.put("catalyst", Constants.MODID + ":staves/" + staffType + defaultCatalyst);
            }

            if ( newFitting != null)
            {
                builder.put("fitting", newFitting.toString());
            } else {
                builder.put("fitting", Constants.MODID + ":staves/" + staffType + defaultFitting);
            }

            ImmutableMap<String, String> map = builder.build();

            switch (staffType)
            {
                case "apprentice":
                    return this.apprenticeModel.retexture(map);
                case "magician":
                    return this.magicianModel.retexture(map);
                case "archmage":
                    return this.archmageModel.retexture(map);
                default:
                    return this.initiateModel.retexture(map);
            }
        }
    }

    public class BakedStaffModel implements IBakedModel
    {
        private HashMap<String, IBakedModel> cache = new HashMap<>();
        private ModelBakery bakery;
        private Function<ResourceLocation, TextureAtlasSprite> spriteGetter;
        private ISprite sprite;
        private VertexFormat format;
        private IBakedModel def;
        private ItemOverrideList overrides;
        private StaffModel parent;

        public BakedStaffModel(StaffModel parent, IBakedModel def, ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
        {
            this.parent = parent;
            this.def = def;
            this.bakery = bakery;
            this.spriteGetter = spriteGetter;
            this.sprite = sprite;
            this.format = format;
            this.overrides = new StaffModelOverrideList(bakery);
        }

        @SuppressWarnings("deprecation")
        @Override
        public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand)
        {
            return def.getQuads(state, side, rand);
        }

        @Override
        public boolean isAmbientOcclusion()
        {
            return def.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d()
        {
            return def.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer()
        {
            return def.isBuiltInRenderer();
        }

        @SuppressWarnings("deprecation")
        @Override
        public TextureAtlasSprite getParticleTexture()
        {
            return def.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides() {
            return overrides;
        }
    }



    public class StaffModelOverrideList extends ItemOverrideList
    {

        private ModelBakery bakery;
        private Function<ResourceLocation, TextureAtlasSprite> textureGetter;

        public StaffModelOverrideList(ModelBakery bakery)
        {
            super();
            this.bakery = bakery;
            this.textureGetter = location -> Minecraft.getInstance().getTextureMap().getAtlasSprite(location.toString());
        }

        @Override
        public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn)
        {
            BakedStaffModel bakedStaffModel = (BakedStaffModel) model;
            CompoundNBT nbt = stack.getOrCreateTag();

            StaffModel staffModel = bakedStaffModel.parent;

            String staffType = nbt.contains("staffType") ? nbt.getString("staffType") : "initiate";
            String rod = nbt.contains("rod") ? nbt.getString("rod") : "BLANK";
            String catalyst = nbt.contains("catalyst") ? nbt.getString("catalyst") : "BLANK";
            String fitting = nbt.contains("fitting") ? nbt.getString("fitting") : "BLANK";

            String cacheKey = staffType + rod + catalyst + fitting;

            if (!bakedStaffModel.cache.containsKey(cacheKey))
            {
                ResourceLocation rodRL = (rod.equals("BLANK")) ? null : new ResourceLocation(Constants.MODID, "staves/" + rod + "_rod");

                ResourceLocation catalystRL = ( catalyst.equals("BLANK") ) ? null : new ResourceLocation(Constants.MODID, "staves/" + staffType + "_catalyst");

                ResourceLocation fittingRL = ( fitting.equals("BLANK")) ? null : new ResourceLocation(Constants.MODID, "staves/" + staffType + "_" + fitting + "_fittings");


                IUnbakedModel newModel = staffModel.retexture(staffType, rodRL, catalystRL, fittingRL);

                bakedStaffModel.cache.put(cacheKey, newModel.bake(this.bakery, this.textureGetter, bakedStaffModel.sprite, bakedStaffModel.format));
            }
            return bakedStaffModel.cache.get(cacheKey);
        }
    }
}
