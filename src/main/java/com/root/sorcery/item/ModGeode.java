package com.root.sorcery.item;

/**
 * Registration for Geode items.
 */
public class ModGeode extends ModItem
{


    public static GeodeItem geodeItem;

    public static void init()
    {

        geodeItem = new GeodeItem("geode_item");

    }
}
