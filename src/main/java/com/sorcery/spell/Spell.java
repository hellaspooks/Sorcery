package com.sorcery.spell;

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
    public int castFrequency = 1;

    public Spell(int arcanaCost)
    {
        this.arcanaCost = arcanaCost;
    }

    /**
     * Called when a spell "finishes"
     * @param context
     * @return
     */
    public ActionResultType castFinal(SpellUseContext context)
    {

        if (!preCast(context))
        {
            return ActionResultType.FAIL;
        }

        if (!context.getWorld().isRemote())
        {
            doCastFinal(context);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Called every tick a spell is being cast.
     * Spell's castFrequency defines how many ticks between procs.
     * @param context
     * @return
     */
    public ActionResultType castPerTick(SpellUseContext context)
    {
        if (!(context.getWorld().getGameTime() % this.castFrequency == 0))
        {
            return ActionResultType.PASS;
        }

        if (!preCast(context))
        {
            return ActionResultType.FAIL;
        }

        if (!context.getWorld().isRemote())
        {
            doCastPerTick(context);
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Checks to see if the spell can be cast.
     * @param context
     * @return
     */
    public boolean preCast(SpellUseContext context)
    {
        if (!allowCast(context))
        {
            return false;
        }

        if (!drainArcana(context, this.getArcanaCost(context)))
        {
            return false;
        }

        return true;
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

    // perform the final cast of the spell
    public ActionResultType doCastFinal(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }

    // perform the per-tick action of the spell
    public ActionResultType doCastPerTick(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }

    // Send packets to play particle effects
    public void doParticleEffects(SpellUseContext context)
    {
    }

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

    protected double getCastPercent(SpellUseContext context)
    {
        if (this.castType == CastType.INSTANT)
        {
            return 1;
        } else {
            return (double)context.getCastingTicks() / (double)this.castDuration;
        }
    }


}
