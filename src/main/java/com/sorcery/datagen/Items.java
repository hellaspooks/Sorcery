package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.item.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class Items extends ItemModelProvider
{

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleSingleTexture(ModItem.TOME_ABJURATION.get(), "tome_abjuration");
        simpleSingleTexture(ModItem.TOME_CONJURATION.get(), "tome_conjuration");
        simpleSingleTexture(ModItem.TOME_ENCHANTMENT.get(), "tome_enchantment");
        simpleSingleTexture(ModItem.TOME_EVOCATION.get(), "tome_evocation");
        simpleSingleTexture(ModItem.TOME_NECROMANCY.get(), "tome_necromancy");
        simpleSingleTexture(ModItem.TOME_TRANSMUTATION.get(), "tome_transmutation");
        simpleSingleTexture(ModItem.ARCANE_DYNAMO.get(), "arcane_dynamo");
        simpleSingleTexture(ModItem.MUNDANE_MECHANISM.get(), "mundane_mechanism");
        simpleSingleTexture(ModItem.LODESTONE.get(), "lodestone");
        simpleSingleTexture(ModItem.WOLFRAM_INGOT.get(), "wolfram_ingot");
        simpleSingleTexture(ModItem.MYTHRIL_INGOT.get(), "mythril_ingot");
        simpleSingleTexture(ModItem.SIGIL_SLATE.get(), "sigil_slate");
        simpleSingleTexture(ModItem.SIGIL_EVOCATION.get(), "sigil_evocation");
        simpleSingleTexture(ModItem.SIGIL_CONJURATION.get(), "sigil_conjuration");
        simpleSingleTexture(ModItem.SIGIL_ABJURATION.get(), "sigil_abjuration");
        simpleSingleTexture(ModItem.SIGIL_ENCHANTMENT.get(), "sigil_enchantment");
        simpleSingleTexture(ModItem.SIGIL_NECROMANCY.get(), "sigil_necromancy");
        simpleSingleTexture(ModItem.SIGIL_TRANSMUTATION.get(), "sigil_transmutation");
        simpleSingleTexture(ModItem.CRYSTAL_ARCANE.get(), "crystal_arcane");
        simpleSingleTexture(ModItem.CRYSTAL_INERT.get(), "crystal_inert");
        simpleSingleTexture(ModItem.CRYSTAL_CARNELIAN.get(), "crystal_carnelian");
        simpleSingleTexture(ModItem.CRYSTAL_CHALCEDONY.get(), "crystal_chalcedony");
        simpleSingleTexture(ModItem.CRYSTAL_SUGILITE.get(), "crystal_sugilite");
        simpleSingleTexture(ModItem.CRYSTAL_JASPER.get(), "crystal_jasper");
        simpleSingleTexture(ModItem.CRYSTAL_SERPENTINE.get(), "crystal_serpentine");
        simpleSingleTexture(ModItem.CRYSTAL_NUUMMITE.get(), "crystal_nuummite");
        simpleSingleTexture(ModItem.GEODE.get(), "geode");
        simpleSingleTexture(ModItem.CRUSHED_WOLFRAMITE.get(), "hadean_ember");
        simpleSingleTexture(ModItem.GRIMOIRE.get(), "grimoire");
        simpleSingleTexture(ModItem.SPELL_BOOK.get(), "tome_evocation");
        // simpleSingleTexture(ModItem.SORCEROUS_STAFF.get(), "apprentice_staff");
        simpleSingleTexture(ModItem.CRYSTAL_RESONATOR.get(), "crystal_resonator");

        // Spell Scrolls
        simpleSingleTexture(ModItem.CREATE_WATER_SPELL_SCROLL.get(), "scroll_evocation");
        simpleSingleTexture(ModItem.REMOVE_ARCANA_SPELL_SCROLL.get(), "scroll_evocation");
        simpleSingleTexture(ModItem.COMBUSTION_SPELL_SCROLL.get(), "scroll_evocation");
        simpleSingleTexture(ModItem.BLINK_SPELL_SCROLL.get(), "scroll_evocation");

    }

    public void simpleSingleTexture(Item item, String pathName)
    {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Constants.MODID, "item/"+pathName));
    }

}