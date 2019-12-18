package com.root.sorcery.network.packets;

import com.root.sorcery.tileentity.StaffLatheTile;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class StaffCraftPacket
{
    private CompoundNBT posNBT;

    public StaffCraftPacket(){}

    public StaffCraftPacket(CompoundNBT nbtIn)
    {
        this.posNBT = nbtIn;
    }

    public StaffCraftPacket(BlockPos pos, int cost, boolean unCraft)
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("x", pos.getX());
        nbt.putInt("y", pos.getY());
        nbt.putInt("z", pos.getZ());
        nbt.putInt("c", cost);
        nbt.putBoolean("u", unCraft);
        this.posNBT = nbt;
    }

    public static void encode(StaffCraftPacket pkt, PacketBuffer buf)
    {
        buf.writeCompoundTag(pkt.posNBT);
    }

    public static StaffCraftPacket decode(PacketBuffer buf)
    {
        return new StaffCraftPacket(buf.readCompoundTag());
    }

    public static class Handler
    {
        public static void handle(final StaffCraftPacket message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                if (ctx.get().getDirection().getReceptionSide().isServer()) {
                    World world = ctx.get().getSender().getEntityWorld();
                    if (!world.isRemote)
                    {
                        BlockPos pos = new BlockPos(message.posNBT.getInt("x"), message.posNBT.getInt("y"), message.posNBT.getInt("z"));
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile instanceof StaffLatheTile)
                        {
                            if (message.posNBT.getBoolean("u"))
                            {
                                ((StaffLatheTile)tile).uncraft();
                            } else {
                                ((StaffLatheTile)tile).startCraft(message.posNBT.getInt("c"));
                            }
                        }

                    } else {
                        System.out.println("in remote ?");
                    }
                }
            });
            ctx.get().getPacketHandled();
        }
    }


}
