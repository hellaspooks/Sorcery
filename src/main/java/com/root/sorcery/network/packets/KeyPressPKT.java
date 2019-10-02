package com.root.sorcery.network.packets;

import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyPressPKT
{
    private final int key = 1;

    public KeyPressPKT(){}

    public static void encode(KeyPressPKT pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.key);
    }

    public static KeyPressPKT decode(PacketBuffer buf)
    {
        return new KeyPressPKT();
    }

    public static class Handler
    {
        public static void handle(final KeyPressPKT message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                System.out.println("Handling Packet");
                ServerPlayerEntity player = ctx.get().getSender();
                ISpellcasting playerCap = player.getCapability(SpellcastingProvider.SPELLCASTING).orElseThrow(NullPointerException::new);

                playerCap.cycleActiveSpell();

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
