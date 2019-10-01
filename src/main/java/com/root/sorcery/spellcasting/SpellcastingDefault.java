package com.root.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.Collections;
import java.util.Set;

public class SpellcastingDefault implements ISpellcasting
{
    private ResourceLocation activeSpell = new ResourceLocation("sorcery:testspell");

    private Set<ResourceLocation> allSpells = Collections.emptySet();

    public SpellcastingDefault(){}

    @Override
    public ResourceLocation getActiveSpell()
    {
        return this.activeSpell;
    }

    @Override
    public void setActiveSpell(ResourceLocation spell)
    {
        this.activeSpell = spell;

    }

    @Override
    public Set<ResourceLocation> getAllSpells()
    {
        return this.allSpells;
    }

    @Override
    public void setAllSpells(Set<ResourceLocation> allSpells)
    {
        this.allSpells = allSpells;
    }

    @Override
    public void addSpell(ResourceLocation spell)
    {
        this.allSpells.add(spell);

    }

    @Override
    public void removeSpell(ResourceLocation spell)
    {
        this.allSpells.remove(spell);

    }

    @Override
    public boolean hasSpell(ResourceLocation spell)
    {
        return this.allSpells.contains(spell);
    }

    // Huh

    public CompoundNBT serializeNBT()
    {

        CompoundNBT tag = new CompoundNBT();
        // Active Spell
        tag.putString("activespell", this.getActiveSpell().toString());

        // All Spells
        ListNBT spellList = new ListNBT();

        for (ResourceLocation spell : this.getAllSpells())
        {
            spellList.add(new StringNBT(spell.toString()));
        }

        tag.put("allspells", spellList);

        return tag;
    }

    public void deserializeNBT(CompoundNBT nbt)
    {
        // Active Spell
        this.setActiveSpell(new ResourceLocation(((CompoundNBT) nbt).getString("activespell")));

        // All Spells
        Set<ResourceLocation> allSpells = Collections.<ResourceLocation>emptySet();

        ListNBT spellList = ((CompoundNBT) nbt).getList("activespells", Constants.NBT.TAG_LIST);

        int size = spellList.size();

        for ( int i = 0; i < size; i++)
        {
            allSpells.add( new ResourceLocation(spellList.get(i).toString()));
        }

        this.setAllSpells(allSpells);

    }


}
