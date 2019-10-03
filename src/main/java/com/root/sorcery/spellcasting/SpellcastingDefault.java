package com.root.sorcery.spellcasting;

import com.root.sorcery.spell.ModSpell;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class SpellcastingDefault implements ISpellcasting
{
    private ResourceLocation activeSpell;

    private ArrayList<ResourceLocation> preparedSpells = new ArrayList<>();

    private ArrayList<ResourceLocation> knownSpells = new ArrayList<>();

    public SpellcastingDefault()
    {

        // Adding defaults for testing purposes


        ResourceLocation testSpell = ModSpell.TEST_SPELL.getRegistryName();
        ResourceLocation testSpell2 = ModSpell.TEST_SPELL_2.getRegistryName();
        ResourceLocation hasteSpell = ModSpell.HASTE_SPELL.getRegistryName();
        ResourceLocation blinkSpell = ModSpell.BLINK_SPELL.getRegistryName();

        this.activeSpell = testSpell;

        this.preparedSpells.add(testSpell);
        this.preparedSpells.add(testSpell2);
        this.preparedSpells.add(hasteSpell);
        this.preparedSpells.add(blinkSpell);

        this.knownSpells.add(testSpell);
        this.knownSpells.add(testSpell2);
        this.knownSpells.add(hasteSpell);
        this.knownSpells.add(blinkSpell);
    }

    public void cycleActiveSpell()
    {
        int activeSpellIndex = preparedSpells.indexOf(activeSpell);
        int nextSpellIndex = activeSpellIndex + 1;

        if (nextSpellIndex == preparedSpells.size())
        {
            this.activeSpell = preparedSpells.get(0);
        }
        else
        {
            this.activeSpell = preparedSpells.get(nextSpellIndex);
        }
    }


    @Override
    public ResourceLocation getActiveSpell()
    {
        return activeSpell;
    }

    @Override
    public void setActiveSpell(ResourceLocation spell)
    {
        activeSpell = spell;

    }

    @Override
    public ArrayList<ResourceLocation> getPreparedSpells()
    {
        return preparedSpells;

    }

    @Override
    public void setPreparedSpells(ArrayList<ResourceLocation> allSpells)
    {
        preparedSpells = allSpells;

    }

    @Override
    public void addPreparedSpell(ResourceLocation spell)
    {
        if (!preparedSpells.contains(spell))
            preparedSpells.add(spell);

    }

    @Override
    public void removePreparedSpell(ResourceLocation spell)
    {
        preparedSpells.remove(spell);

    }

    @Override
    public boolean hasPreparedSpell(ResourceLocation spell)
    {
        return preparedSpells.contains(spell);
    }

    @Override
    public ArrayList<ResourceLocation> getKnownSpells()
    {
        return knownSpells;
    }

    @Override
    public void setKnownSpells(ArrayList<ResourceLocation> allSpells)
    {
        knownSpells = allSpells;
    }

    @Override
    public void addKnownSpell(ResourceLocation spell)
    {
        if (!knownSpells.contains(spell))
            knownSpells.add(spell);

    }

    @Override
    public void removeKnownSpell(ResourceLocation spell)
    {
        knownSpells.remove(spell);

    }

    @Override
    public boolean hasKnownSpell(ResourceLocation spell)
    {
        return knownSpells.contains(spell);
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
