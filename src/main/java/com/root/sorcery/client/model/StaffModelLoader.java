package com.root.sorcery.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.root.sorcery.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.extensions.IForgeUnbakedModel;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class StaffModelLoader implements IModelLoader
{

    public StaffModelLoader()
    {

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
    }

    @Override
    public IModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents)
    {
        return null;
    }

    public class StaffModel implements IForgeUnbakedModel
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
            this.initiateModel = ModelLoader.instance().getModelOrLogError(initiateModelRL, "model missing");
            this.apprenticeModel = ModelLoader.instance().getModelOrLogError(apprenticeModelRL, "model missing");
            this.magicianModel = ModelLoader.instance().getModelOrLogError(magicianModelRL, "model missing");
            this.archmageModel = ModelLoader.instance().getModelOrLogError(archmageModelRL, "model missing");
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
        public IForgeBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format)
        {
            IUnbakedModel retexBase = this.retexture(null, null, null, null);

            IForgeBakedModel bakedBase = retexBase.bakeModel(bakery, spriteGetter, sprite, format);
            return new BakedStaffModel(this, bakedBase, bakery, spriteGetter, format);
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

    public class BakedStaffModel implements IForgeBakedModel
    {
        private HashMap<String, IBakedModel> cache = new HashMap<>();
        private ModelBakery bakery;
        private Function<ResourceLocation, TextureAtlasSprite> spriteGetter;
        private VertexFormat format;
        private IForgeBakedModel def;
        private ItemOverrideList overrides;
        private StaffModel parent;
        private EmptyModelData modelData;

        public BakedStaffModel(StaffModel parent, IForgeBakedModel def, ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, VertexFormat format)
        {
            this.parent = parent;
            this.def = def;
            this.bakery = bakery;
            this.spriteGetter = spriteGetter;
            this.format = format;
            this.modelData = EmptyModelData.INSTANCE;
            this.overrides = new StaffModelOverrideList(bakery);
        }
    }


    public class StaffModelOverrideList extends ItemOverrideList
    {

        private ModelBakery bakery;
        private Function<Material, TextureAtlasSprite> textureGetter;

        public StaffModelOverrideList(ModelBakery bakery)
        {
            super();
            this.bakery = bakery;
            this.textureGetter = (mat) -> mat.getSprite();
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

                bakedStaffModel.cache.put(cacheKey, newModel.bakeModel(this.bakery, this.textureGetter, SimpleModelTransform.IDENTITY, new ResourceLocation(Constants.MODID, "item/sorcerous_staff")));
            }
            return bakedStaffModel.cache.get(cacheKey);
        }
    }
}
