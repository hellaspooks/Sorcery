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


    // Prepared Spells

    public Set<ResourceLocation> getPreparedSpells();

    public void setPreparedSpells(Set<ResourceLocation> allSpells);

    public void addPreparedSpell(ResourceLocation spell);

    public void removePreparedSpell(ResourceLocation spell);

    public boolean hasPreparedSpell(ResourceLocation spell);


    // Known Spells

    public Set<ResourceLocation> getKnownSpells();

    public void setKnownSpells(Set<ResourceLocation> allSpells);

    public void addKnownSpell(ResourceLocation spell);

    public void removeKnownSpell(ResourceLocation spell);

    public boolean hasKnownSpell(ResourceLocation spell);
}
