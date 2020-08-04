package com.root.sorcery.setup;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.ArcanaProvider;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.item.PortableArcanaItem;
import com.root.sorcery.item.SpellGrantingItem;
import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.network.packets.SpellCapSyncPacket;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import com.root.sorcery.spellcasting.SpellcastingProvider;
import com.root.sorcery.tileentity.AbstractMonolithTile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// Event Handlers
@Mod.EventBusSubscriber
public class CommonEventHandlers
{

    @SubscribeEvent
    public static void attachCapabilitiesEntities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesItems(AttachCapabilitiesEvent<ItemStack> event)
    {
        if (event.getObject().getItem() instanceof SpellcastingItem)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
            event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
        }
        if (event.getObject().getItem() instanceof SpellGrantingItem)
        {
            event.addCapability(SpellcastingCapability.SPELLCASTING_LOC, new SpellcastingProvider());
        }
        if (event.getObject().getItem() instanceof PortableArcanaItem)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesTileEntities(AttachCapabilitiesEvent<TileEntity> event)
    {
        if (event.getObject().getTileEntity() instanceof ReliquaryTile)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        } else if (event.getObject().getTileEntity() instanceof AbstractMonolithTile)
        {
            event.addCapability(ArcanaCapability.ARCANA_LOC, new ArcanaProvider());
        }
    }

    @SubscribeEvent
    public static void playerCloneEvent(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            ISpellcasting origCap = event.getOriginal().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
            ISpellcasting newCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
            newCap.setActiveSpell(origCap.getActiveSpell());
            newCap.setPreparedSpells(origCap.getPreparedSpells());
            newCap.setKnownSpells(origCap.getKnownSpells());
        }
    }

    @SubscribeEvent
    public static void playerLoginEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        IArcanaStorage playerArcanaCap = event.getPlayer().getCapability(ArcanaCapability.ARCANA, null).orElseThrow(NullPointerException::new);
        ISpellcasting playerSpellCap = event.getPlayer().getCapability(SpellcastingCapability.SPELLCASTING, null).orElseThrow(NullPointerException::new);

        ServerPlayerEntity serverPlayer = event.getPlayer().getServer().getPlayerList().getPlayerByUUID(event.getPlayer().getUniqueID());

        PacketHandler.sendToPlayer(serverPlayer, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerSpellCap, null)));
        PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerArcanaCap, null)));



    }

}
