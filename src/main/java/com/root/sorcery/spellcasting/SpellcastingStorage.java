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
        tag.putString("active_spell", instance.getActiveSpell().toString());

        // Prepared Spells
        ListNBT preparedSpells = new ListNBT();

        for (ResourceLocation spell : instance.getPreparedSpells())
        {
            preparedSpells.add(new StringNBT(spell.toString()));
        }

        tag.put("prepared_spells", preparedSpells);

        // Known Spells
        ListNBT knownSpells = new ListNBT();

        for (ResourceLocation spell : instance.getKnownSpells())
        {
            knownSpells.add(new StringNBT(spell.toString()));
        }

        tag.put("known_spells", knownSpells);

        return tag;
    }

    @Override
    public void readNBT(Capability<ISpellcasting> capability, ISpellcasting instance, Direction side, INBT nbt)
    {

        // Active Spell
        instance.setActiveSpell(new ResourceLocation(((CompoundNBT) nbt).getString("activespell")));

        // Prepared Spells
        Set<ResourceLocation> preparedSpells = Collections.emptySet();

        ListNBT preparedSpellList = ((CompoundNBT) nbt).getList("prepared_spells", Constants.NBT.TAG_LIST);

        int size = preparedSpellList.size();

        for ( int i = 0; i < size; i++)
        {
            preparedSpells.add( new ResourceLocation(preparedSpellList.get(i).toString()));
        }

        instance.setPreparedSpells(preparedSpells);

        // Known Spells
        Set<ResourceLocation> knownSpells = Collections.<ResourceLocation>emptySet();

        ListNBT knownSpellList = ((CompoundNBT) nbt).getList("activespells", Constants.NBT.TAG_LIST);

        int size1 = knownSpellList.size();

        for ( int i = 0; i < size1; i++)
        {
            knownSpells.add( new ResourceLocation(knownSpellList.get(i).toString()));
        }

        instance.setKnownSpells(knownSpells);
    }
}
