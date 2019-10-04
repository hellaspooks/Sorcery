package com.root.sorcery.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
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

    // placeholder for now, will eventually be how arcana is drained, cast on true, fail on false.
    public boolean drainArcana(int arcanaAmount)
    {
        return true;
    }

}
