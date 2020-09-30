package com.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;

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
            preparedSpells.add(StringNBT.valueOf(spell.toString()));
        }

        tag.put("prepared_spells", preparedSpells);

        // Known Spells
        ListNBT knownSpells = new ListNBT();

        tag.put("known_spells", knownSpells);

        return tag;
    }

    @Override
    public void readNBT(Capability<ISpellcasting> capability, ISpellcasting instance, Direction side, INBT nbt)
    {

        // Active Spell
        instance.setActiveSpell(new ResourceLocation(((CompoundNBT) nbt).getString("active_spell")));

        // Prepared Spells
        ArrayList<ResourceLocation> preparedSpells = new ArrayList<>();

        ListNBT preparedSpellList = (ListNBT) ((CompoundNBT) nbt).get("prepared_spells");

        int size = preparedSpellList.size();

        for ( int i = 0; i < size; i++)
        {
            preparedSpells.add(new ResourceLocation(preparedSpellList.getString(i)));
        }

        instance.setPreparedSpells(preparedSpells);

    }
}
