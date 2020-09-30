package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.item.ModItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider
{

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(ModItem.create_water_spell_scroll.getRegistryName().getPath(), new ResourceLocation("item/handheld"),
                "layer0", new ResourceLocation(Constants.MODID, "item/scroll_evocation"));
    }
}