package com.root.sorcery.network.packets;

import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


import java.util.function.Supplier;

import static com.root.sorcery.spellcasting.SpellcastingCapability.SPELLCASTING_STORAGE;

public class SpellCapSyncPacket
{
   private CompoundNBT capNBT;

   public SpellCapSyncPacket(){}

   public SpellCapSyncPacket(CompoundNBT nbtIn)
   {
      this.capNBT = nbtIn;
   }

   public static void encode(SpellCapSyncPacket pkt, PacketBuffer buf)
   {
      buf.writeCompoundTag(pkt.capNBT);

   }

   public static SpellCapSyncPacket decode(PacketBuffer buf)
   {
      return new SpellCapSyncPacket(buf.readCompoundTag());
   }

   public static class Handler
   {
      public static void handle(final SpellCapSyncPacket message, Supplier<NetworkEvent.Context> ctx)
      {
         ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {

               ISpellcasting playerCap = Minecraft.getInstance().player.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);

               SPELLCASTING_STORAGE.readNBT(SpellcastingCapability.SPELLCASTING, playerCap, null, message.capNBT);
            } else {
               ISpellcasting playerCap = ctx.get().getSender().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
               SPELLCASTING_STORAGE.readNBT(SpellcastingCapability.SPELLCASTING, playerCap, null, message.capNBT);
            }

         });
         ctx.get().getPacketHandled();
      }
   }


}
