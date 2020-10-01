package com.sorcery.spellcasting;

import com.sorcery.spell.ModSpell;
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

        ResourceLocation testSpell = ModSpell.TEST_SPELL.getId();

        this.activeSpell = testSpell;

        this.preparedSpells.add(testSpell);

        this.knownSpells.add(testSpell);
    }

    public void cycleActiveSpell(int delta)
    {
        if (delta >= 0)
        {
            this.activeSpell = this.getNextSpell();
        } else {
            this.activeSpell = this.getPreviousSpell();
        }
    }

    @Override
    public int getIndexFromSpell(ResourceLocation rl)
    {
        return preparedSpells.indexOf(rl);
    }

    @Override
    public ResourceLocation getSpellFromIndex(int index)
    {
        return preparedSpells.get(index);
    }

    @Override
    public ResourceLocation getNextSpell()
    {
        int nextSpellIndex = preparedSpells.indexOf(this.activeSpell) + 1;

        if (nextSpellIndex >= preparedSpells.size())
        {
            return preparedSpells.get(0);
        } else {
            return preparedSpells.get(nextSpellIndex);
        }
    }

    @Override
    public ResourceLocation getPreviousSpell()
    {
        int previousSpellIndex = preparedSpells.indexOf(activeSpell) - 1;

        if (previousSpellIndex < 0)
        {
            return preparedSpells.get(preparedSpells.size() - 1);
        }
        else
        {
            return preparedSpells.get(previousSpellIndex);
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
    public void clearPreparedSpells()
    {
        preparedSpells.clear();
    }

    public void deserializeNBT(CompoundNBT nbt)
    {
        SpellcastingCapability.SPELLCASTING_STORAGE.readNBT(SpellcastingCapability.SPELLCASTING, this, null, nbt);
    }

    public CompoundNBT serializeNBT()
    {
        INBT tag = SpellcastingCapability.SPELLCASTING_STORAGE.writeNBT(SpellcastingCapability.SPELLCASTING, this, null);
        return (CompoundNBT) tag;
    }

}
