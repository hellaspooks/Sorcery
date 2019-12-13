package com.root.sorcery.item;

import net.minecraft.item.Item;

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
}
