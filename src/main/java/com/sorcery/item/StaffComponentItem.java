package com.sorcery.item;

import com.sorcery.Constants;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StaffComponentItem extends Item
{
    public String modelString;
    public int arcanaCost;

    public StaffComponentItem(Properties properties, String modelString, int arcanaCost)
    {
        super(properties);
        this.modelString = modelString;
        this.arcanaCost = arcanaCost;
    }

    public static StaffComponentItem getComponent(String type, String name)
    {
        ResourceLocation itemRL = new ResourceLocation(Constants.MODID, name + "_" + type);

        Item item = GameRegistry.findRegistry(Item.class).getValue(itemRL);

        if (item instanceof StaffComponentItem)
        {
            return (StaffComponentItem) item;
        } else {
            return null;
        }
    }
}
