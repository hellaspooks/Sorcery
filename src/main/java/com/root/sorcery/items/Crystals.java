package com.root.sorcery.items;

import net.minecraft.item.Item;

public class Crystals extends ModItem {
    Crystals(String registryName) {
        super(registryName);
    }

    public static Crystals carnelian;
    public static Crystals chalcedony;
    public static Crystals sugilite;
    public static Crystals jasper;
    public static Crystals serpentine;
    public static Crystals nuummite;

    public static void init() {
        carnelian = new Crystals("crystal1");
        chalcedony = new Crystals("crystal2");
        sugilite = new Crystals("crystal3");
        jasper = new Crystals("crystal4");
        serpentine = new Crystals("crystal5");
        nuummite = new Crystals("crystal6");
    }

    public static boolean isCrystal(Item item) {
        if (item == null)
            return false;

        if (item == carnelian.getItem()
                || item == chalcedony.getItem()
                || item == sugilite.getItem()
                || item == jasper.getItem()
                || item == serpentine.getItem()
                || item == nuummite.getItem())
            return true;
        return false;
    }

    public static Crystals getRandomCrystal() {
        //add some kind of randomizer here
        return null;
    }
}
