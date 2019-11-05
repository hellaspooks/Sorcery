package com.root.sorcery.network;

import com.root.sorcery.Constants;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.network.packets.KeyPressPacket;
import com.root.sorcery.network.packets.ParticleEffectPacket;
import com.root.sorcery.network.packets.SpellCapSyncPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Constants.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();


    public static void init()
    {
        int disc = 0;


        HANDLER.registerMessage(disc++, KeyPressPacket.class, KeyPressPacket::encode, KeyPressPacket::decode, KeyPressPacket.Handler::handle);
        HANDLER.registerMessage(disc++, SpellCapSyncPacket.class, SpellCapSyncPacket::encode, SpellCapSyncPacket::decode, SpellCapSyncPacket.Handler::handle);
        HANDLER.registerMessage(disc++, ArcanaCapSyncPacket.class, ArcanaCapSyncPacket::encode, ArcanaCapSyncPacket::decode, ArcanaCapSyncPacket.Handler::handle);
        HANDLER.registerMessage(disc++, ParticleEffectPacket.class, ParticleEffectPacket::encode, ParticleEffectPacket::decode, ParticleEffectPacket.Handler::handle);
    }

    public static void sendToServer(Object msg)
    {
        HANDLER.sendToServer(msg);
    }


    public static void sendToPlayer(ServerPlayerEntity player, Object msg)
    {
        HANDLER.send(PacketDistributor.PLAYER.with(()->player), msg);
    }

    public static void sendToAllTracking(Entity entity, Object msg)
    {
        HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(()->entity), msg);
    }
}
