package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{


    public Spell()
    {

    }

    public ActionResultType cast(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }
}
