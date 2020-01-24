package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{
    public int arcanaCost;
    public int castDuration = 1;
    public SoundEvent sound = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    public SoundCategory soundCategory = SoundCategory.BLOCKS;
    public CastType castType = CastType.INSTANT;

    public Spell(int arcanaCost)
    {
        this.arcanaCost = arcanaCost;
    }

    // Main casting method, mostly dispatch to castServer and castClient methods
    // In general this shouldn't need to be overwritten
    public ActionResultType cast(SpellUseContext context)
    {

        if (!allowCast(context))
        {
            return ActionResultType.FAIL;
        }

        if (!drainArcana(context, this.getArcanaCost(context)))
        {
            return ActionResultType.FAIL;
        }

        playSound(context);

        if (!context.getWorld().isRemote())
        {
            castServer(context);
            doParticleEffects(context);
            return ActionResultType.SUCCESS;
        } else {
            return castClient(context);
        }
    }

    // check to see if cast will be allowed
    public boolean allowCast(SpellUseContext context)
    {
        return true;
    }

    // get arcana cost. override to define variable arcana costs
    public int getArcanaCost(SpellUseContext context)
    {
        return this.arcanaCost;
    }

    // Server-side only stuff happens here
    public ActionResultType castServer(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }

    // Client-side only stuff happens here
    public ActionResultType castClient(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }

    // Send packets to play particle effects
    public void doParticleEffects(SpellUseContext context){}

    // Play sound effects, override if you want different behavior
    public void playSound(SpellUseContext context)
    {
        context.getWorld().playSound(context.getPlayer(), context.getPos(), this.sound, this.soundCategory, 1.0F, context.getWorld().rand.nextFloat() * 0.4F + 0.8F);
    }


    // Drain Arcana from caster, return true if successful
    public boolean drainArcana(SpellUseContext context, int arcanaCost)
    {
        if (!context.getWorld().isRemote())
        {
            // Server side, check + drain
            if (context.getArcanaSource().getArcanaStored() >= arcanaCost)
            {
                context.getArcanaSource().extractArcana(arcanaCost, false);
                return true;
            } else {
                return false;
            }
        } else {
            // Client side, just check
            if (context.getArcanaSource().getArcanaStored() >= arcanaCost)
            {
                return true;
            } else {
                return false;
            }
        }
    }

    public int getCastDuration(){
        return this.castDuration;
    }

    public CastType getCastType()
    {
        return this.castType;
    }


}
