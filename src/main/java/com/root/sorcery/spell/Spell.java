package com.root.sorcery.spell;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class Spell extends ForgeRegistryEntry<Spell>
{
    public int castDuration = 0;
    public int arcanaCost = 0;
    public SoundEvent sound = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    public SoundCategory soundCategory = SoundCategory.BLOCKS;

    public Spell()
    {
    }

    // Main casting method, mostly dispatch to castServer and castClient methods
    // In general this shouldn't need to be overwritten
    public ActionResultType cast(SpellUseContext context)
    {

        // Try to drain Arcana, Server side only
        if (!context.getWorld().isRemote()) {
            if (!drainArcana(context, this.arcanaCost))
                return ActionResultType.FAIL;
        }

        // If arcana was successfully drained, cast the client+server spell components, and play sounds
        this.playSound(context);

        if (context.getWorld().isRemote())
        {
            castClient(context);
            return ActionResultType.SUCCESS;
        }
        else
        {
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
            // Debug message for development
            context.getPlayer().sendMessage(new StringTextComponent(String.format("Remaining Arcana: %d", context.getArcanaSource().getArcanaStored())));

            if(!context.getWorld().isRemote())
            {
                // Sync arcana with client
                ServerPlayerEntity serverPlayer = context.getWorld().getServer().getPlayerList().getPlayerByUUID(context.getPlayer().getUniqueID());
                PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(context.getArcanaSource(), null)));
            }
            return true;
        }
        else
        {
            return false;
        }

    }

    public int getCastDuration(){
        return castDuration;
    }


}
