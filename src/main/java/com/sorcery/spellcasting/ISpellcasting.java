package com.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public interface ISpellcasting extends INBTSerializable<CompoundNBT>
{
    // Active Spell
    public ResourceLocation getActiveSpell();

    public void setActiveSpell(ResourceLocation spell);


    // Prepared Spells

    public ArrayList<ResourceLocation> getPreparedSpells();

    public void setPreparedSpells(ArrayList<ResourceLocation> allSpells);

    public void addPreparedSpell(ResourceLocation spell);

    public void removePreparedSpell(ResourceLocation spell);

    public boolean hasPreparedSpell(ResourceLocation spell);


    // Known Spells

    public ArrayList<ResourceLocation> getKnownSpells();

    public void setKnownSpells(ArrayList<ResourceLocation> allSpells);

    public void addKnownSpell(ResourceLocation spell);

    public void removeKnownSpell(ResourceLocation spell);

    public boolean hasKnownSpell(ResourceLocation spell);

    // Utility

    public void cycleActiveSpell(int delta);

    public int getIndexFromSpell(ResourceLocation rl);

    public ResourceLocation getSpellFromIndex(int index);

    public ResourceLocation getNextSpell();

    public ResourceLocation getPreviousSpell();

}
