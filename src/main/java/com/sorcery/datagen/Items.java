package com.sorcery.datagen;

import com.sorcery.Constants;
import com.sorcery.block.ModBlock;
import com.sorcery.item.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
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


        // Block Item Models
        simpleBlockItem(ModItem.POLISHED_RUNESTONE.get(), "polished_runestone");
        simpleBlockItem(ModItem.RUNEWOOD_LOG.get(), "runewood_log");
        simpleBlockItem(ModItem.STRIPPED_RUNEWOOD_LOG.get(), "stripped_runewood_log");
        simpleBlockItem(ModItem.RUNEWOOD_LEAVES.get(), "runewood_leaves");
        simpleBlockItem(ModItem.RUNEWOOD_SAPLING.get(), "runewood_sapling");
        simpleBlockItem(ModItem.RUNESTONE_BRICKS.get(), "runestone_bricks");
        simpleBlockItem(ModItem.RUNESTONE_BRICK_SLAB.get(), "runestone_brick_slab");
        simpleBlockItem(ModItem.RUNESTONE_BRICK_STAIRS.get(), "runestone_brick_stairs");
        simpleBlockItem(ModItem.RUNESTONE_BRICK_WALL.get(), "runestone_brick_wall_inventory");
        simpleBlockItem(ModItem.RUNEWOOD_PLANKS.get(), "runewood_planks");
        simpleBlockItem(ModItem.RUNEWOOD_PLANK_SLAB.get(), "runewood_plank_slab");
        simpleBlockItem(ModItem.RUNEWOOD_PLANK_STAIRS.get(), "runewood_plank_stairs");
        simpleBlockItem(ModItem.RUNEWOOD_PLANK_FENCE.get(), "runewood_plank_fence_inventory");
        simpleBlockItem(ModItem.MONOLITH_CHISELED.get(), "monolith_chiseled");
        simpleBlockItem(ModItem.MONOLITH_SOLAR.get(), "monolith_solar");
        simpleBlockItem(ModItem.MONOLITH_LUNAR.get(), "monolith_lunar");
        simpleBlockItem(ModItem.MONOLITH_DARK.get(), "monolith_dark");
        simpleBlockItem(ModItem.CHISELED_RUNESTONE.get(), "chiseled_runestone");
        simpleBlockItem(ModItem.DARK_RUNESTONE.get(), "dark_runestone");
        simpleBlockItem(ModItem.LAPIS_RUNESTONE.get(), "lapis_runestone");
        simpleBlockItem(ModItem.LUNAR_RUNESTONE.get(), "lunar_runestone");
        simpleBlockItem(ModItem.SOLAR_RUNESTONE.get(), "solar_runestone");
        simpleBlockItem(ModItem.WOLFRAM_LANTERN.get(), "wolfram_lantern");
        simpleBlockItem(ModItem.PYLON.get(), "pylon_inactive");


    }

    public void simpleSingleTexture(Item item, String pathName)
    {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(Constants.MODID, "item/"+pathName));
    }

    public void simpleBlockItem(Item item, String pathName)
    {
        withExistingParent(item.getRegistryName().getPath(), new ResourceLocation(Constants.MODID, "block/" + pathName));
    }

}