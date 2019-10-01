package com.root.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class SpellcastingStorage implements Capability.IStorage<ISpellcasting>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ISpellcasting> capability, ISpellcasting instance, Direction side)
    {
        CompoundNBT tag = new CompoundNBT();
        // Active Spell
        tag.putString("activespell", instance.getActiveSpell().toString());

        // All Spells
        ListNBT spellList = new ListNBT();

        for (ResourceLocation spell : instance.getAllSpells())
        {
            spellList.add(new StringNBT(spell.toString()));
        }

        tag.put("allspells", spellList);

        return tag;
    }

    @Override
    public void readNBT(Capability<ISpellcasting> capability, ISpellcasting instance, Direction side, INBT nbt)
    {
        // Active Spell
        instance.setActiveSpell(new ResourceLocation(((CompoundNBT) nbt).getString("activespell")));

        // All Spells
        Set<ResourceLocation> allSpells = Collections.<ResourceLocation>emptySet();

        ListNBT spellList = ((CompoundNBT) nbt).getList("activespells", Constants.NBT.TAG_LIST);

        int size = spellList.size();

        for ( int i = 0; i < size; i++)
        {
            allSpells.add( new ResourceLocation(spellList.get(i).toString()));
        }

        instance.setAllSpells(allSpells);

    }
}
