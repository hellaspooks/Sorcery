package com.root.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Set;

public interface ISpellcasting extends INBTSerializable<CompoundNBT>
{
    // Active Spell
    public ResourceLocation getActiveSpell();

    public void setActiveSpell(ResourceLocation spell);


    // Learned Spells

    public Set<ResourceLocation> getAllSpells();

    public void setAllSpells(Set<ResourceLocation> allSpells);

    public void addSpell(ResourceLocation spell);

    public void removeSpell(ResourceLocation spell);

    public boolean hasSpell(ResourceLocation spell);
}
