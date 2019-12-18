package com.root.sorcery;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

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
}
