package com.sorcery.network.packets;

import com.sorcery.item.SpellbookItem;
import com.sorcery.spellcasting.ISpellcasting;
import com.sorcery.spellcasting.SpellcastingCapability;
import com.sorcery.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

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
        CompoundNBT tag = buf.readCompoundTag();
        return new SpellCapSyncPacket(tag);
    }

    public static class Handler
    {
        public static void handle(final SpellCapSyncPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getDirection().getReceptionSide().isClient()) {

                    PlayerEntity playerEntity = Minecraft.getInstance().player;
                    ItemStack spellbook = Utils.getPlayerSpellbook(playerEntity);
                    System.out.println("In spell cap sync client");
                    if (spellbook == null)
                    {
                        return;
                    }
                    if (spellbook.getItem() instanceof SpellbookItem)
                    {
                        System.out.println("In spell cap sync client, got spellbook");
                        ISpellcasting itemCap = Utils.getSpellCap(spellbook);
                        itemCap = Utils.getSpellCap(spellbook);
                        System.out.println("existing nbt:");
                        System.out.println(itemCap.serializeNBT());
                        System.out.println("sent nbt:");
                        System.out.println(message.capNBT);
                        SpellcastingCapability.SPELLCASTING.readNBT(itemCap, null, message.capNBT);
                    }
                } else {
                    PlayerEntity playerEntity = ctx.get().getSender();
                    ItemStack spellbook = Utils.getPlayerSpellbook(playerEntity);
                    if (spellbook == null)
                    {
                        return;
                    }
                    System.out.println("In spell cap sync server");
                    if (spellbook.getItem() instanceof SpellbookItem)
                    {
                        System.out.println("In spell cap sync client, got spellbook");
                        Utils.getSpellCap(spellbook);
                        ISpellcasting itemCap;
                        itemCap = Utils.getSpellCap(spellbook);
                        System.out.println("existing nbt:");
                        System.out.println(itemCap.serializeNBT());
                        System.out.println("sent nbt:");
                        System.out.println(message.capNBT);
                        SpellcastingCapability.SPELLCASTING.readNBT(itemCap, null, message.capNBT);
                    }
                }

            });
            ctx.get().getPacketHandled();
        }
    }

}
