package com.sorcery.network.packets;

import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellCapSyncPacket
{
    private CompoundNBT capNBT;

    private int slot;

    public SpellCapSyncPacket(){}

    public SpellCapSyncPacket(CompoundNBT nbtIn)
    {
        this.capNBT = nbtIn;
        this.slot = -1;
    }

    public SpellCapSyncPacket(CompoundNBT nbtIn, int slot)
    {
        this.capNBT = nbtIn;
        this.slot = slot;
    }

    public static void encode(SpellCapSyncPacket pkt, PacketBuffer buf)
    {
        buf.writeCompoundTag(pkt.capNBT);
        buf.writeInt(pkt.slot);
    }

    public static SpellCapSyncPacket decode(PacketBuffer buf)
    {
        CompoundNBT tag = buf.readCompoundTag();
        int slot = buf.readInt();
        return new SpellCapSyncPacket(tag, slot);
    }

    public static class Handler
    {
        public static void handle(final SpellCapSyncPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getDirection().getReceptionSide().isClient()) {

                    ISpellcasting playerCap = Minecraft.getInstance().player.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                    SpellcastingCapability.SPELLCASTING.readNBT(playerCap, null, message.capNBT);

                    if (message.slot > -1)
                    {
                        ISpellcasting spellCap = Utils.getSpellCap(Minecraft.getInstance().player.inventory.getStackInSlot(message.slot));
                        SpellcastingCapability.SPELLCASTING.readNBT(spellCap, null, message.capNBT);
                    }
                } else {
                    ISpellcasting playerCap = ctx.get().getSender().getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                    SpellcastingCapability.SPELLCASTING.readNBT(playerCap, null, message.capNBT);
                    if (message.slot > -1) {
                        ISpellcasting spellCap = Utils.getSpellCap(ctx.get().getSender().inventory.getStackInSlot(message.slot));
                        SpellcastingCapability.SPELLCASTING.readNBT(spellCap, null, message.capNBT);
                    }
                }

            });
            ctx.get().getPacketHandled();
        }
    }

}
