package com.root.sorcery.item;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static com.root.sorcery.item.ModItem.carnelian;
import static com.root.sorcery.item.ModItem.chalcedony;
import static com.root.sorcery.item.ModItem.jasper;
import static com.root.sorcery.item.ModItem.nuummite;
import static com.root.sorcery.item.ModItem.serpentine;
import static com.root.sorcery.item.ModItem.sugilite;


public class CrystalItem extends ItemMod
{

    public CrystalItem()
    {
        super();
    }


    public static ItemStack getRandomCrystal()
    {
        int randomNumb = (int) ((Math.random() * 6) + 1);

        switch (randomNumb)
        {
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

    public static ArrayList<ItemStack> getRandomCrystal(int amount)
    {
        int randomNumb;
        ArrayList<ItemStack> crystals = new ArrayList<>();

        int carn = 0, cha = 0, sug = 0, jas = 0, ser = 0, nuu = 0;

        for (int i = 0; i < amount; i++)
        {
            randomNumb = (int) (Math.random() * 6 + 1);
            switch (randomNumb)
            {
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
