package com.root.sorcery;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Constants
{
    public static final String MODID = "sorcery";
    public static final String MODNAME = "Sorcery";
    public static final String VERSION = "0.1.0";
    public static final Item.Properties ITEM_PROPS = new Item.Properties().group(ModSetup.sorcery);
    public static final Item.Properties ITEM_PROPS_NONSTACK = new Item.Properties().group(ModSetup.sorcery).maxStackSize(1);

    public static final ResourceLocation CATALYST_TAG = new ResourceLocation(MODID, "catalysts");
    public static final ResourceLocation FITTING_TAG = new ResourceLocation(MODID, "fittings");
    public static final ResourceLocation ROD_TAG = new ResourceLocation(MODID, "rods");

    public static final ResourceLocation MONOLITH_FORMABLE_TAG = new ResourceLocation(MODID, "monolith_formable");
    public static final ResourceLocation PLANT_DEATHABLE_TAG = new ResourceLocation(MODID, "plant_deathable");

    public static final List EVOCATION_RED_HIGHLIGHT = Arrays.asList(208, 3, 35);
    public static final List EVOCATION_RED_MAIN = Arrays.asList(153, 0, 36);
    public static final List EVOCATION_RED_LOWLIGHT = Arrays.asList(91, 2, 48);

    public static final List TRANSMUTATION_GREEN_HIGHLIGHT = Arrays.asList(145, 239, 59);
    public static final List TRANSMUTATION_GREEN_MAIN = Arrays.asList(53, 187, 93);
    public static final List TRANSMUTATION_GREEN_LOWLIGHT = Arrays.asList(23, 95, 66);

    public static final List CONJURATION_PURPLE_HIGHLIGHT = Arrays.asList(216, 124, 242);
    public static final List CONJURATION_PURPLE_MAIN = Arrays.asList(135, 74, 203);
    public static final List CONJURATION_PURPLE_LOWLIGHT = Arrays.asList(79, 52, 141);

    public static final List NECROMANCY_BLACK_HIGHLIGHT = Arrays.asList(73, 78, 93);
    public static final List NECROMANCY_BLACK_MAIN = Arrays.asList(38, 41, 49);
    public static final List NECROMANCY_BLACK_LOWLIGHT = Arrays.asList(22, 24, 31);

    public static final List ABJURATION_YELLOW_HIGHLIGHT = Arrays.asList(253, 225, 95);
    public static final List ABJURATION_YELLOW_MAIN = Arrays.asList(234, 171, 46);
    public static final List ABJURATION_YELLOW_LOWLIGHT = Arrays.asList(203, 106, 23);

    public static final List ENCHANTMENT_BLUE_HIGHLIGHT = Arrays.asList(16, 155, 219);
    public static final List ENCHANTMENT_BLUE_MAIN = Arrays.asList(24, 89, 183);
    public static final List ENCHANTMENT_BLUE_LOWLIGHT = Arrays.asList(24, 89, 183);

    public static final List ARCANA_PURPLE_HIGHLIGHT = Arrays.asList(195, 27, 167);
    public static final List ARCANA_PURPLE_MAIN = Arrays.asList(151, 24, 174);
    public static final List ARCANA_PURPLE_LOWLIGHT = Arrays.asList(102, 13, 157);

    public static final List SOLAR_GOLD_HIGHLIGHT = Arrays.asList(255, 250, 155);
    public static final List SOLAR_GOLD_MAIN = Arrays.asList(255, 198, 28);
    public static final List SOLAR_GOLD_LOWLIGHT = Arrays.asList(228, 131, 14);

    public static final List LUNAR_SILVER_HIGHLIGHT = Arrays.asList(211, 247, 243);
    public static final List LUNAR_SILVER_MAIN = Arrays.asList(177, 222, 228);
    public static final List LUNAR_SILVER_LOWLIGHT = Arrays.asList(136, 184, 211);

    public static final List LAPIS_BLUE_HIGHLIGHT = Arrays.asList(62, 177, 228);
    public static final List LAPIS_BLUE_MAIN = Arrays.asList(60, 110, 216);
    public static final List LAPIS_BLUE_LOWLIGHT = Arrays.asList(57, 73, 195);

    public static final List BLOOD_RED_HIGHLIGHT = Arrays.asList(143, 1, 11);
    public static final List BLOOD_RED_MAIN = Arrays.asList(118, 1, 44);
    public static final List BLOOD_RED_LOWLIGHT = Arrays.asList(95, 1, 61);

}
