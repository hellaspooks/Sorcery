package com.root.sorcery;

import com.root.sorcery.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static ArrayList<List> arcanaColors = new ArrayList<List>() {
        {
            add(Arrays.asList(178, 54, 160));
            add(Arrays.asList(93, 33, 150));
            add(Arrays.asList(138, 46, 166));
        }
    };

}
