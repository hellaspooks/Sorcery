package com.sorcery.network.packets;

import com.sorcery.Sorcery;
import com.sorcery.network.PacketHandler;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.utils.Utils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyPressPacket
{
    private int key;

    public KeyPressPacket(){}

    public KeyPressPacket(int keyIn)
    {
        this.key = keyIn;
    }

    public static void encode(KeyPressPacket pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.key);
    }

    public static KeyPressPacket decode(PacketBuffer buf)
    {
        return new KeyPressPacket(buf.readVarInt());
    }

    public static class Handler
    {
        public static void handle(final KeyPressPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                ISpellcasting playerCap = Utils.getSpellCap(player);
                ISpellcasting itemCap = Utils.getSpellCap(player.getHeldItemMainhand());
                switch (message.key)
                {
                    // Cycle Spell Key
                    case 1:

                        playerCap.cycleActiveSpell(1);
                        itemCap.cycleActiveSpell(1);

                        //player.sendMessage(new StringTextComponent(String.format("Active Spell is now: %s", playerCap.getActiveSpell().toString())));

                        // Sync player capability to client
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerCap, null)));
                        break;
                    case 2:

                        playerCap.cycleActiveSpell(-1);
                        itemCap.cycleActiveSpell(-1);

                        //player.sendMessage(new StringTextComponent(String.format("Active Spell is now: %s", playerCap.getActiveSpell().toString())));

                        // Sync player capability to client
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerCap, null)));
                        break;
                    default:
                        Sorcery.getLogger().debug("hit default in keypress packet");
                        break;
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
