package com.root.sorcery.spell;

import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{
    public int arcanaCost;
    public int castDuration = 0;
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

        // Try to drain Arcana
        if (!drainArcana(context, this.arcanaCost))
            return ActionResultType.FAIL;

        // If arcana was successfully drained, cast the client+server spell components, and play sounds
        this.playSound(context);

        if (context.getWorld().isRemote())
        {
            castClient(context);
            return ActionResultType.SUCCESS;
        }
        else
        {
            doParticleEffects(context);
            return castServer(context);
        }
    }

    // Server-side only stuff happens here
    public ActionResultType castServer(SpellUseContext context)
    {
        return ActionResultType.SUCCESS;
    }

    // Client-side only stuff happens here
    public void castClient(SpellUseContext context)
    {

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
        if (context.getArcanaSource().getArcanaStored() >= arcanaCost){
            context.getArcanaSource().extractArcana(arcanaCost, false);


            // Unnecssary if arcana is on item?
            //if(!context.getWorld().isRemote())
            //{
                // Sync arcana with client
              //  ServerPlayerEntity serverPlayer = context.getWorld().getServer().getPlayerList().getPlayerByUUID(context.getPlayer().getUniqueID());
              //  PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(context.getArcanaSource(), null)));
            //}

            return true;
        }
        else
        {
            return false;
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
