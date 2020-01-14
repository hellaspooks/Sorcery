package com.root.sorcery.network.packets;

import com.root.sorcery.item.SpellcastingItem;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.spellcasting.ISpellcasting;
import com.root.sorcery.spellcasting.SpellcastingCapability;
import com.root.sorcery.utils.Utils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
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
                ISpellcasting playerCap = player.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                ItemStack item = player.getHeldItemMainhand();
                ISpellcasting itemCap = player.getCapability(SpellcastingCapability.SPELLCASTING).orElseThrow(NullPointerException::new);
                int slot = player.inventory.getSlotFor(item);
                switch (message.key)
                {
                    // Cycle Spell Key
                    case 1:

                        playerCap.cycleActiveSpell(1);

                        if (item.getItem() instanceof SpellcastingItem)
                        {
                            CompoundNBT nbt = item.getTag();
                            nbt.putInt("spellDuration", Utils.getSpellFromProvider(player).castDuration);
                            item.setTag(nbt);
                        }

                        //player.sendMessage(new StringTextComponent(String.format("Active Spell is now: %s", playerCap.getActiveSpell().toString())));

                        // Sync capability to client
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerCap, null), slot));
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerCap, null)));
                        break;
                    case 2:

                        playerCap.cycleActiveSpell(-1);

                        if (item.getItem() instanceof SpellcastingItem)
                        {
                            CompoundNBT nbt = item.getTag();
                            nbt.putInt("spellDuration", Utils.getSpellFromProvider(player).castDuration);
                            item.setTag(nbt);
                        }

                        //player.sendMessage(new StringTextComponent(String.format("Active Spell is now: %s", playerCap.getActiveSpell().toString())));

                        // Sync capability to client
                        PacketHandler.sendToPlayer(player, new SpellCapSyncPacket((CompoundNBT) SpellcastingCapability.SPELLCASTING.writeNBT(playerCap, null)));
                        break;
                    default:
                        System.out.println("hit default");
                        break;
                }

            });
            ctx.get().setPacketHandled(true);
        }
    }
}
