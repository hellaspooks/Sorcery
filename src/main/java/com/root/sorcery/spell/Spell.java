package com.root.sorcery.spell;

import com.root.sorcery.arcana.IArcanaStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{
    public int castDuration = 0;

    public Spell()
    {
    }

    public ActionResultType cast(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }


    // placeholder for now, will eventually be how arcana is drained, cast on true, fail on false.
    public boolean drainArcana(SpellUseContext context, int arcanaCost)
    {
        if (context.getArcanaSource().getArcanaStored() >= arcanaCost){
            context.getArcanaSource().extractArcana(arcanaCost, false);
            // For testing:
            context.getPlayer().sendMessage(new StringTextComponent(String.format("Remaining Arcana: %d", context.getArcanaSource().getArcanaStored())));
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getCastDuration(){
        System.out.println("Getting Cast Duration");
        return castDuration;
    }

    public void doSpellEffects(SpellUseContext context){}

}
