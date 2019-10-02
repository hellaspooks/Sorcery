package com.root.sorcery.spellcasting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class SpellcastingDefault implements ISpellcasting
{
    private ResourceLocation activeSpell = new ResourceLocation("sorcery:testspell");

    private Set<ResourceLocation> preparedSpells = Collections.emptySet();

    private Set<ResourceLocation> knownSpells = Collections.emptySet();

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
    public Set<ResourceLocation> getPreparedSpells()
    {
        return this.preparedSpells;

    }

    @Override
    public void setPreparedSpells(Set<ResourceLocation> allSpells)
    {
        this.preparedSpells = allSpells;

    }

    @Override
    public void addPreparedSpell(ResourceLocation spell)
    {
        this.preparedSpells.add(spell);

    }

    @Override
    public void removePreparedSpell(ResourceLocation spell)
    {
        this.preparedSpells.remove(spell);

    }

    @Override
    public boolean hasPreparedSpell(ResourceLocation spell)
    {
        return this.preparedSpells.contains(spell);
    }

    @Override
    public Set<ResourceLocation> getKnownSpells()
    {
        return this.knownSpells;
    }

    @Override
    public void setKnownSpells(Set<ResourceLocation> allSpells)
    {
        this.knownSpells = allSpells;
    }

    @Override
    public void addKnownSpell(ResourceLocation spell)
    {
        this.knownSpells.add(spell);

    }

    @Override
    public void removeKnownSpell(ResourceLocation spell)
    {
        this.knownSpells.remove(spell);

    }

    @Override
    public boolean hasKnownSpell(ResourceLocation spell)
    {
        return this.knownSpells.contains(spell);
    }

    public void deserializeNBT(CompoundNBT nbt)
    {
        SpellcastingCapability.SPELL_STORAGE.readNBT(SpellcastingProvider.SPELLCASTING, this, null, nbt);
    }

    public CompoundNBT serializeNBT()
    {
        INBT tag = SpellcastingCapability.SPELL_STORAGE.writeNBT(SpellcastingProvider.SPELLCASTING, this, null);
        return (CompoundNBT) tag;
    }

}
