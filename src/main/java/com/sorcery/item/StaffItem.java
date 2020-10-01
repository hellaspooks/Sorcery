package com.sorcery.item;


import com.sorcery.spell.Spell;
import com.sorcery.spell.SpellUseContext;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public class StaffItem extends SpellcastingItem
{
    public StaffItem(Properties properties)
    {
        super(properties);
    }

    public static StaffComponentItem getRod(ItemStack staff)
    {
        CompoundNBT tag = staff.getOrCreateTag();
        if (tag.contains("rod"))
        {
            String rodType = tag.getString("rod");
            return StaffComponentItem.getComponent("rod", rodType);
        }
        return null;
    }

    public static StaffComponentItem getCatalyst(ItemStack staff)
    {
        CompoundNBT tag = staff.getOrCreateTag();
        if (tag.contains("catalyst"))
        {
            String catalystType = tag.getString("catalyst");
            return StaffComponentItem.getComponent("catalyst", catalystType);
        }
        return null;
    }

    public static StaffComponentItem getFittings(ItemStack staff)
    {
        CompoundNBT tag = staff.getOrCreateTag();
        if (tag.contains("fitting"))
        {
            String fittingsType = tag.getString("fitting");
            return StaffComponentItem.getComponent("fittings", fittingsType);
        }
        return null;
    }

    public static ArrayList<StaffComponentItem> getAllComponents(ItemStack staff)
    {
        ArrayList<StaffComponentItem> allComps = new ArrayList<>(3);
        allComps.add(0, getRod(staff));
        allComps.add(1, getCatalyst(staff));
        allComps.add(2, getFittings(staff));
        return allComps;
    }

    public static boolean areStaffsEqual(ItemStack staff1, ItemStack staff2)
    {
        boolean flag = true;
        ArrayList<StaffComponentItem> comps1 = getAllComponents(staff1);
        ArrayList<StaffComponentItem> comps2 = getAllComponents(staff2);

        for (int i = 0; i < comps1.size(); i ++)
        {
            if (comps1.get(i).getRegistryName() != comps2.get(i).getRegistryName())
            {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 10;
    }

    @Override
    public Spell getActiveSpell(SpellUseContext context)
    {
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack stack = Utils.getPlayerSpellbook(playerEntity);
        if (stack != null)
        {
            return Utils.getSpellFromProvider(stack);
        }
        return Utils.getSpellFromProvider(context.getItem());
    }

}
