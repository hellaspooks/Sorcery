package com.sorcery.network.packets;

import com.sorcery.arcana.ArcanaCapability;
import com.sorcery.arcana.IArcanaStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ArcanaCapSyncPacket
{
   private CompoundNBT capNBT;

   public ArcanaCapSyncPacket(){}

   public ArcanaCapSyncPacket(CompoundNBT nbtIn)
   {
      this.capNBT = nbtIn;
   }

   public ArcanaCapSyncPacket(INBT nbtIn)
   {
      IntNBT intNBTin = (IntNBT) nbtIn;
      this.capNBT = new CompoundNBT();
      this.capNBT.putInt("arcana", intNBTin.getInt());
   }

   public static void encode(ArcanaCapSyncPacket pkt, PacketBuffer buf)
   {
      buf.writeCompoundTag(pkt.capNBT);

   }

   public static ArcanaCapSyncPacket decode(PacketBuffer buf)
   {
      return new ArcanaCapSyncPacket(buf.readCompoundTag());
   }

   public static class Handler
   {
      public static void handle(final ArcanaCapSyncPacket message, Supplier<NetworkEvent.Context> ctx)
      {
         ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {

               IArcanaStorage playerCap = Minecraft.getInstance().player.getCapability(ArcanaCapability.ARCANA).orElseThrow(NullPointerException::new);

               ArcanaCapability.ARCANA.readNBT(playerCap, null, message.capNBT);
            } else {
               IArcanaStorage playerCap = ctx.get().getSender().getCapability(ArcanaCapability.ARCANA).orElseThrow(NullPointerException::new);
               ArcanaCapability.ARCANA.readNBT(playerCap, null, message.capNBT);
            }

         });
         ctx.get().getPacketHandled();
      }
   }


}
