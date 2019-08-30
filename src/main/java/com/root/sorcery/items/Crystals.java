package com.root.sorcery.items;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Crystals extends ModItem {

    private Crystals(String registryName) {
        super(registryName);
    }

    public static Crystals carnelian;
    public static Crystals chalcedony;
    public static Crystals sugilite;
    public static Crystals jasper;
    public static Crystals serpentine;
    public static Crystals nuummite;

    public static void init() {
        carnelian = new Crystals("carnelian");
        chalcedony = new Crystals("chalcedony");
        sugilite = new Crystals("sugilite");
        jasper = new Crystals("jasper");
        serpentine = new Crystals("serpentine");
        nuummite = new Crystals("nuumite");
    }

    public static ItemStack getRandomCrystal() {
        int randomNumb = (int) (Math.random() * 6 + 1);

        switch (randomNumb) {
            case 1:
                return new ItemStack(carnelian.getItem());
            case 2:
                return new ItemStack(chalcedony.getItem());
            case 3:
                return new ItemStack(sugilite.getItem());
            case 4:
                return new ItemStack(jasper.getItem());
            case 5:
                return new ItemStack(serpentine.getItem());
            case 6:
                return new ItemStack(nuummite.getItem());
            default:
                return null;
        }
    }

    public static ArrayList<ItemStack> getRandomCrystal(int amount) {
        int randomNumb;
        ArrayList<ItemStack> crystals = new ArrayList<>();

        int carn = 0, cha = 0, sug = 0, jas = 0, ser = 0, nuu = 0;

        for (int i = 0; i < amount; i++) {
            randomNumb = (int) (Math.random() * 6 + 1);
            switch (randomNumb) {
                case 1:
                    carn++;
                    break;
                case 2:
                    cha++;
                    break;
                case 3:
                    sug++;
                    break;
                case 4:
                    jas++;
                    break;
                case 5:
                    ser++;
                    break;
                case 6:
                    nuu++;
                    break;
            }
        }

        if (carn != 0)
            crystals.add(new ItemStack(carnelian.getItem(), carn));
        if (cha != 0)
            crystals.add(new ItemStack(chalcedony.getItem(), cha));
        if (sug != 0)
            crystals.add(new ItemStack(sugilite.getItem(), sug));
        if (jas != 0)
            crystals.add(new ItemStack(jasper.getItem(), jas));
        if (ser != 0)
            crystals.add(new ItemStack(serpentine.getItem(), ser));
        if (nuu != 0)
            crystals.add(new ItemStack(nuummite.getItem(), nuu));

        return crystals;
    }
}
