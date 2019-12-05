package com.root.sorcery.block;

import com.root.sorcery.arcana.ArcanaCapability;
import com.root.sorcery.arcana.IArcanaStorage;
import com.root.sorcery.network.PacketHandler;
import com.root.sorcery.network.packets.ArcanaCapSyncPacket;
import com.root.sorcery.tileentity.PylonTile;
import com.root.sorcery.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PylonBlock extends Block
{
    private static Float hardness = 3.0F;
    private static Float resistance = 6.0F;

    public PylonBlock()
    {
        super(Properties.create(Material.ROCK).hardnessAndResistance( hardness, resistance).sound(SoundType.STONE));
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        System.out.println("created phylactery tile");
        return new PylonTile();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);

            if (tile instanceof PylonTile) {

                // for dev
                player.sendStatusMessage(new StringTextComponent(String.format("Stored Arcana: %d", ((PylonTile) tile).getStoredArcana())), true);

                if (player.isSneaking())
                {
                    return true;
                }

                IArcanaStorage playerCap = Utils.getArcanaCap(player);
                int arcanaExtracted = ((PylonTile) tile).getArcanaForPlayer();
                playerCap.receiveArcana(arcanaExtracted, false);

                // Sync arcana with client
                ServerPlayerEntity serverPlayer = worldIn.getServer().getPlayerList().getPlayerByUUID(player.getUniqueID());
                PacketHandler.sendToPlayer(serverPlayer, new ArcanaCapSyncPacket(ArcanaCapability.ARCANA.writeNBT(playerCap, null)));

                return true;
            }
        }
        return false;
    }
}
