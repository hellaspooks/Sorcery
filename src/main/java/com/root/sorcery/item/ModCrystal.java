package com.root.sorcery.item;

/**
 * Registration for Crystal items.
 */
public class ModCrystal extends ModItem
{


    public static CrystalItem carnelian;
    public static CrystalItem chalcedony;
    public static CrystalItem sugilite;
    public static CrystalItem jasper;
    public static CrystalItem serpentine;
    public static CrystalItem nuummite;

    public static void init()
    {
        carnelian = new CrystalItem("carnelian");
        chalcedony = new CrystalItem("chalcedony");
        sugilite = new CrystalItem("sugilite");
        jasper = new CrystalItem("jasper");
        serpentine = new CrystalItem("serpentine");
        nuummite = new CrystalItem("nuummite");
    }
}
